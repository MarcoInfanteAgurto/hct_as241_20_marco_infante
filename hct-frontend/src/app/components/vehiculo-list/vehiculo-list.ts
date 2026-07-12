import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Vehiculo } from '../../models/vehiculo';
import { VehiculoService } from '../../services/vehiculo';

@Component({
  selector: 'app-vehiculo-list',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './vehiculo-list.html',
  styleUrl: './vehiculo-list.css',
})
export class VehiculoList implements OnInit {
  vehiculos: Vehiculo[] = [];
  editando: boolean = false;
  vehiculoEdicionId: string | null = null;

  nuevoVehiculo: Vehiculo = {
    placa: '',
    marca: '',
    modelo: '',
    anio: 2026,
    color: '',
    precioPoDia: 0
  };

  constructor(
    private vehiculoService: VehiculoService,
    private cdr: ChangeDetectorRef
  ) { }

  ngOnInit(): void {
    this.cargarVehiculos();
  }

  cargarVehiculos(): void {
    this.vehiculoService.listar().subscribe(data => {
      this.vehiculos = data;
      this.cdr.detectChanges();
    });
  }

  crear(): void {
    if (this.editando && this.vehiculoEdicionId) {
      this.vehiculoService.actualizar(this.vehiculoEdicionId, this.nuevoVehiculo).subscribe(() => {
        this.cargarVehiculos();
        this.limpiarFormulario();
      });
    } else {
      this.vehiculoService.crear(this.nuevoVehiculo).subscribe(() => {
        this.cargarVehiculos();
        this.limpiarFormulario();
      });
    }
  }

  iniciarEdicion(vehiculo: Vehiculo): void {
    this.editando = true;
    this.vehiculoEdicionId = vehiculo.id || null;
    this.nuevoVehiculo = { ...vehiculo };
  }

  cancelarEdicion(): void {
    this.limpiarFormulario();
  }

  desactivar(id: string): void {
    this.vehiculoService.desactivar(id).subscribe(() => {
      this.cargarVehiculos();
    });
  }

  activar(id: string): void {
    this.vehiculoService.activar(id).subscribe(() => {
      this.cargarVehiculos();
    });
  }

  eliminar(id: string): void {
    this.vehiculoService.eliminar(id).subscribe(() => {
      this.cargarVehiculos();
    });
  }

  limpiarFormulario(): void {
    this.editando = false;
    this.vehiculoEdicionId = null;
    this.nuevoVehiculo = {
      placa: '',
      marca: '',
      modelo: '',
      anio: 2026,
      color: '',
      precioPoDia: 0
    };
  }
}