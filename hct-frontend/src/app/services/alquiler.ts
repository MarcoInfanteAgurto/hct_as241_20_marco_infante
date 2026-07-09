import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Alquiler } from '../models/alquiler';

@Injectable({
  providedIn: 'root',
})
export class AlquilerService {
  private baseUrl = 'http://localhost:8083/api/alquileres';

  constructor(private http: HttpClient) { }

  listar(): Observable<Alquiler[]> {
    return this.http.get<Alquiler[]>(this.baseUrl);
  }

  buscarPorId(id: string): Observable<Alquiler> {
    return this.http.get<Alquiler>(`${this.baseUrl}/${id}`);
  }

  crear(alquiler: Alquiler): Observable<Alquiler> {
    return this.http.post<Alquiler>(this.baseUrl, alquiler);
  }

  eliminar(id: string): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }

  activar(id: string): Observable<Alquiler> {
    return this.http.put<Alquiler>(`${this.baseUrl}/${id}/activar`, {});
  }

  desactivar(id: string): Observable<Alquiler> {
    return this.http.put<Alquiler>(`${this.baseUrl}/${id}/desactivar`, {});
  }
}