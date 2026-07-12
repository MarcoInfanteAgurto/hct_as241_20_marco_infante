import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Alquiler } from '../../models/alquiler';
import { AlquilerService } from '../../services/alquiler';
import { Cliente } from '../../models/cliente';
import { Vehiculo } from '../../models/vehiculo';
import { ClienteService } from '../../services/cliente';
import { VehiculoService } from '../../services/vehiculo';

@Component({
  selector: 'app-alquiler-list',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './alquiler-list.html',
  styleUrl: './alquiler-list.css',
})
export class AlquilerList implements OnInit {
  alquileres: Alquiler[] = [];
  clientesActivos: Cliente[] = [];
  vehiculosDisponibles: Vehiculo[] = [];
  editando: boolean = false;
  alquilerEdicionId: string | null = null;

  // diccionarios para busqueda rápida en la tabla
  mapClientes: { [id: string]: Cliente } = {};
  mapVehiculos: { [id: string]: Vehiculo } = {};

  nuevoAlquiler: Alquiler = {
    clienteId: '',
    vehiculoId: '',
    dias: 1,
    fechaInicio: '',
    fechaFin: ''
  };

  constructor(
    private alquilerService: AlquilerService,
    private clienteService: ClienteService,
    private vehiculoService: VehiculoService,
    private cdr: ChangeDetectorRef
  ) { }

  ngOnInit(): void {
    this.cargarDatosRelacionados();
    this.cargarAlquileres();
  }

  cargarDatosRelacionados(): void {
    // Cargar todos los clientes para mostrar datos y para dropdown
    this.clienteService.listar().subscribe(data => {
      // guardamos en el diccionario todos para el listado de la tabla
      data.forEach(c => {
        if (c.id) this.mapClientes[c.id] = c;
      });
      // solo los activos para el formulario de nuevo alquiler
      this.clientesActivos = data.filter(c => c.estado === 'ACTIVO');
      this.cdr.detectChanges();
    });

    // Cargar todos los vehículos para mostrar datos y para dropdown
    this.vehiculoService.listar().subscribe(data => {
      // guardamos en el diccionario todos para el listado de la tabla
      data.forEach(v => {
        if (v.id) this.mapVehiculos[v.id] = v;
      });
      // solo los disponibles para el formulario de nuevo alquiler
      this.vehiculosDisponibles = data.filter(v => v.estado === 'DISPONIBLE');
      this.cdr.detectChanges();
    });
  }

  cargarAlquileres(): void {
    this.alquilerService.listar().subscribe(data => {
      this.alquileres = data;
      this.cdr.detectChanges();
    });
  }

  crear(): void {
    if (this.editando && this.alquilerEdicionId) {
      this.alquilerService.actualizar(this.alquilerEdicionId, this.nuevoAlquiler).subscribe({
        next: () => {
          this.cargarAlquileres();
          this.cargarDatosRelacionados();
          this.limpiarFormulario();
        },
        error: (err) => {
          alert('Error al actualizar alquiler: ' + (err.error?.message || 'verifica los datos'));
        }
      });
    } else {
      this.alquilerService.crear(this.nuevoAlquiler).subscribe({
        next: () => {
          this.cargarAlquileres();
          this.cargarDatosRelacionados(); // Recargar estados por si cambiaron a no disponibles/inactivos
          this.limpiarFormulario();
        },
        error: (err) => {
          alert('Error al crear alquiler: ' + (err.error?.message || 'verifica los IDs y que estén disponibles/activos'));
        }
      });
    }
  }

  iniciarEdicion(alquiler: Alquiler): void {
    this.editando = true;
    this.alquilerEdicionId = alquiler.id || null;
    this.nuevoAlquiler = { ...alquiler };
    
    const cActual = this.mapClientes[alquiler.clienteId];
    if (cActual && !this.clientesActivos.some(c => c.id === cActual.id)) {
      this.clientesActivos.push(cActual);
    }
    const vActual = this.mapVehiculos[alquiler.vehiculoId];
    if (vActual && !this.vehiculosDisponibles.some(v => v.id === vActual.id)) {
      this.vehiculosDisponibles.push(vActual);
    }
  }

  cancelarEdicion(): void {
    this.limpiarFormulario();
  }

  desactivar(id: string): void {
    this.alquilerService.desactivar(id).subscribe(() => {
      this.cargarAlquileres();
      this.cargarDatosRelacionados();
    });
  }

  activar(id: string): void {
    this.alquilerService.activar(id).subscribe(() => {
      this.cargarAlquileres();
      this.cargarDatosRelacionados();
    });
  }

  eliminar(id: string): void {
    this.alquilerService.eliminar(id).subscribe(() => {
      this.cargarAlquileres();
      this.cargarDatosRelacionados();
    });
  }

  limpiarFormulario(): void {
    this.editando = false;
    this.alquilerEdicionId = null;
    this.nuevoAlquiler = {
      clienteId: '',
      vehiculoId: '',
      dias: 1,
      fechaInicio: '',
      fechaFin: ''
    };
    this.cargarDatosRelacionados();
  }

  getClienteDniYNombre(id: string): string {
    const c = this.mapClientes[id];
    return c ? `${c.dni} - ${c.apellidos} ${c.nombres}` : `ID: ${id}`;
  }

  getVehiculoPlacaYInfo(id: string): string {
    const v = this.mapVehiculos[id];
    return v ? `${v.placa} (${v.marca} ${v.modelo})` : `ID: ${id}`;
  }
}