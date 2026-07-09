import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Cliente } from '../models/cliente';

@Injectable({
  providedIn: 'root',
})
export class ClienteService {
  private baseUrl = 'http://localhost:8082/api/clientes';

  constructor(private http: HttpClient) { }

  listar(): Observable<Cliente[]> {
    return this.http.get<Cliente[]>(this.baseUrl);
  }

  buscarPorId(id: string): Observable<Cliente> {
    return this.http.get<Cliente>(`${this.baseUrl}/${id}`);
  }

  crear(cliente: Cliente): Observable<Cliente> {
    return this.http.post<Cliente>(this.baseUrl, cliente);
  }

  actualizar(id: string, cliente: Cliente): Observable<Cliente> {
    return this.http.put<Cliente>(`${this.baseUrl}/${id}`, cliente);
  }

  eliminar(id: string): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }

  activar(id: string): Observable<Cliente> {
    return this.http.put<Cliente>(`${this.baseUrl}/${id}/activar`, {});
  }

  desactivar(id: string): Observable<Cliente> {
    return this.http.put<Cliente>(`${this.baseUrl}/${id}/desactivar`, {});
  }
}