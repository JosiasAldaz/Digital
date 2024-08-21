import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { map } from 'rxjs/operators';
import { Habitaciones } from '../model/Habitaciones';

@Injectable({
  providedIn: 'root'
})
export class HabitacionesService {
  private httpHeaders = new HttpHeaders({ 'Content-Type': 'application/json' })
  private urlEndPoint:string = environment.urlHost+'api/habitaciones';
  constructor(private http:HttpClient) { }

  getAlertas(): Observable<Habitaciones[]> {
    return this.http.get(`${environment.urlHost}api/habitaciones`).pipe(
      map(Response => Response as Habitaciones[])
    );
  }

  listar(): Observable<Habitaciones[]>{
    return this.http.get<Habitaciones[]>(this.urlEndPoint);
  }

  crear(alerta: Habitaciones): Observable<Habitaciones> {
    return this.http.post<Habitaciones>(this.urlEndPoint, alerta, { headers: this.httpHeaders });
  }

  eliminar(id_punto: number): Observable<Habitaciones>{
    return this.http.delete<Habitaciones>(`${this.urlEndPoint}/${id_punto}`)
    }

    actualizar(punto: Habitaciones): Observable<Habitaciones> {
    const id_punto = `${this.urlEndPoint}/${punto.id_habitacion}`;
    return this.http.put<Habitaciones>(id_punto, punto, { headers: this.httpHeaders});
    }
}