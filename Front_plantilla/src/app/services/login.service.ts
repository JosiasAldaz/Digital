import { HttpClient, HttpErrorResponse } from "@angular/common/http";
import { Router } from "@angular/router";
import { environment } from '../../environments/environment';
import { BehaviorSubject, Observable, throwError } from "rxjs";
import { AuthLoginRequest } from "../model/AuthLoginRequest";
import { catchError, map, tap } from "rxjs/operators";
import Swal from "sweetalert2";
import { Injectable } from "@angular/core";

@Injectable({
    providedIn: 'root'
  })

export class LoginService {
    currentUserLoginOn: BehaviorSubject<boolean>;
    currentUserData: BehaviorSubject<string>;

    constructor(private http:HttpClient,private router:Router) {

        this.currentUserLoginOn = new BehaviorSubject<boolean>(false);
        this.currentUserData = new BehaviorSubject<string>("");
    
        // Comprueba si hay un token en el sessionStorage al inicializar el servicio
        const token = sessionStorage.getItem("token");
        const usuario = sessionStorage.getItem("usuario");
        if (token) {
            this.currentUserLoginOn.next(true);
            this.currentUserData.next(token);
        }
      }

      login(credentials: AuthLoginRequest): Observable<any> {
        return this.http.post<any>(environment.urlHost + "auth/login", credentials).pipe(
            tap((userData) => {
                if (userData!==null && userData.jwt) {
                    sessionStorage.setItem("token", userData.jwt);
                    //sessionStorage.setItem("nombre", userData.usuario.persona.nombre);
                    //sessionStorage.setItem("apellido", userData.usuario.persona.apellido);
                    sessionStorage.setItem("usuario", userData.username);
                    this.currentUserData.next(userData.jwt);
                    this.currentUserLoginOn.next(true);
                    Swal.fire({
                        icon: 'success',
                        title: 'Inicio de sesión exitoso',
                        text: 'Bienvenido',
                    });
                    this.router.navigate(['/dashboard']);
                } else {
                    //console.log("Datos de usuario recibidos:", userData);               console.error("Respuesta de inicio de sesión incompleta o sin token.");
                }
            }),
            map((userData) => userData.jwt),
            catchError(this.handleError),
    );
    }

    /* register(credentials: RegisterRequest): Observable<any> {
        return this.http.post<any>(environment.urlHost + "auth/register", credentials).pipe(
            tap((userData) => {
                console.log("Datos de usuario recibidos:", userData);
                if (userData && userData.token) {
                    sessionStorage.setItem("token", userData.token);
                    this.currentUserData.next(userData.token);
                    console.log("Token de sesión almacenado:", userData.token);
                    this.currentUserLoginOn.next(true);
                } else {
                    console.error("Respuesta de inicio de sesión incompleta o sin token.");
                }
            }),
    
            map((userData) => userData.token),
            catchError(this.handleError),
    
        );
    } */


    logout(): void {
        sessionStorage.removeItem("token");
        sessionStorage.removeItem("cedula");
        sessionStorage.removeItem("nombre");
        sessionStorage.removeItem("apellido");
        this.currentUserData.next('');
        this.currentUserLoginOn.next(false);
        console.log("Sesión cerrada. Token eliminado.");
    }
    
    private handleError(error: HttpErrorResponse) {
        if (error.status === 0) {
            console.error('Se ha producido un error:', error);
        } else {
            console.error('Backend retornó el código de estado:', error.status);
        }
        return throwError('Algo falló. Por favor, inténtelo nuevamente.');
      }
}