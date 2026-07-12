import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Cliente } from '../../models/cliente';
import { ClienteService } from '../../services/cliente';

@Component({
  selector: 'app-cliente-list',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './cliente-list.html',
  styleUrl: './cliente-list.css',
})
export class ClienteList implements OnInit {
  clientes: Cliente[] = [];
  editando: boolean = false;
  clienteEdicionId: string | null = null;

  nuevoCliente: Cliente = {
    dni: '',
    nombres: '',
    apellidos: '',
    celular: '',
    correo: '',
    licencia: ''
  };

  constructor(
    private clienteService: ClienteService,
    private cdr: ChangeDetectorRef
  ) { }

  ngOnInit(): void {
    this.cargarClientes();
  }

  cargarClientes(): void {
    this.clienteService.listar().subscribe(data => {
      this.clientes = data;
      this.cdr.detectChanges();
    });
  }

  crear(): void {
    if (this.editando && this.clienteEdicionId) {
      this.clienteService.actualizar(this.clienteEdicionId, this.nuevoCliente).subscribe(() => {
        this.cargarClientes();
        this.limpiarFormulario();
      });
    } else {
      this.clienteService.crear(this.nuevoCliente).subscribe(() => {
        this.cargarClientes();
        this.limpiarFormulario();
      });
    }
  }

  iniciarEdicion(cliente: Cliente): void {
    this.editando = true;
    this.clienteEdicionId = cliente.id || null;
    this.nuevoCliente = { ...cliente };
  }

  cancelarEdicion(): void {
    this.limpiarFormulario();
  }

  desactivar(id: string): void {
    this.clienteService.desactivar(id).subscribe(() => {
      this.cargarClientes();
    });
  }

  activar(id: string): void {
    this.clienteService.activar(id).subscribe(() => {
      this.cargarClientes();
    });
  }

  eliminar(id: string): void {
    this.clienteService.eliminar(id).subscribe(() => {
      this.cargarClientes();
    });
  }

  limpiarFormulario(): void {
    this.editando = false;
    this.clienteEdicionId = null;
    this.nuevoCliente = {
      dni: '',
      nombres: '',
      apellidos: '',
      celular: '',
      correo: '',
      licencia: ''
    };
  }
}