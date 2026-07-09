export interface Vehiculo {
  id?: string;
  placa: string;
  marca: string;
  modelo: string;
  anio: number;
  color: string;
  precioPoDia: number;
  estado?: string;
}