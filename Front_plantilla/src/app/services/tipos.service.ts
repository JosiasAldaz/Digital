import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { map } from 'rxjs/operators';
import { tipos } from '../model/tipos';

@Injectable({
  providedIn: 'root'
})
export class TiposService {
  private httpHeaders = new HttpHeaders({ 'Content-Type': 'application/json' })
  private urlEndPoint:string = environment.urlHost+'api/tipo';
  constructor(private http:HttpClient) { }

  getAlertas(): Observable<tipos[]> {
    return this.http.get(`${environment.urlHost}api/tipo`).pipe(
      map(Response => Response as tipos[])
    );
  }

  buscar(id_punto: number):Observable<tipos>{
    return this.http.get<tipos>(`${this.urlEndPoint}/${id_punto}`);
  }

  listar(): Observable<tipos[]>{
    return this.http.get<tipos[]>(this.urlEndPoint);
  }

  crear(alerta: tipos): Observable<tipos> {
    return this.http.post<tipos>(this.urlEndPoint, alerta, { headers: this.httpHeaders });
  }

  eliminar(id_punto: number): Observable<tipos>{
    return this.http.delete<tipos>(`${this.urlEndPoint}/${id_punto}`)
    }

    actualizar(punto: tipos): Observable<tipos> {
    const id_punto = `${this.urlEndPoint}/${punto.id_tipo}`;
    return this.http.put<tipos>(id_punto, punto, { headers: this.httpHeaders});
    }
}