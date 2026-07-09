import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Vehiculo } from '../models/vehiculo';

@Injectable({
  providedIn: 'root',
})
export class VehiculoService {
  private baseUrl = 'http://localhost:8081/api/vehiculos';

  constructor(private http: HttpClient) { }

  listar(): Observable<Vehiculo[]> {
    return this.http.get<Vehiculo[]>(this.baseUrl);
  }

  buscarPorId(id: string): Observable<Vehiculo> {
    return this.http.get<Vehiculo>(`${this.baseUrl}/${id}`);
  }

  crear(vehiculo: Vehiculo): Observable<Vehiculo> {
    return this.http.post<Vehiculo>(this.baseUrl, vehiculo);
  }

  actualizar(id: string, vehiculo: Vehiculo): Observable<Vehiculo> {
    return this.http.put<Vehiculo>(`${this.baseUrl}/${id}`, vehiculo);
  }

  eliminar(id: string): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }

  activar(id: string): Observable<Vehiculo> {
    return this.http.put<Vehiculo>(`${this.baseUrl}/${id}/activar`, {});
  }

  desactivar(id: string): Observable<Vehiculo> {
    return this.http.put<Vehiculo>(`${this.baseUrl}/${id}/desactivar`, {});
  }
}