import { Routes } from '@angular/router';
import { VehiculoList } from './components/vehiculo-list/vehiculo-list';
import { ClienteList } from './components/cliente-list/cliente-list';
import { AlquilerList } from './components/alquiler-list/alquiler-list';

export const routes: Routes = [
  { path: '', redirectTo: 'vehiculos', pathMatch: 'full' },
  { path: 'vehiculos', component: VehiculoList },
  { path: 'clientes', component: ClienteList },
  { path: 'alquileres', component: AlquilerList }
];