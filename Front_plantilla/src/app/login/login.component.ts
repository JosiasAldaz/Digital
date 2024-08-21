import { Component } from '@angular/core';
import { BehaviorSubject, Observable, throwError } from 'rxjs';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { AuthLoginRequest } from '../model/AuthLoginRequest';
import { Router } from '@angular/router';
import { catchError, map, tap } from 'rxjs/operators';
import Swal from 'sweetalert2';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { LoginService } from '../services/login.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
})
export class LoginComponent {
  loginForm: FormGroup;
  currentUserLoginOn: BehaviorSubject<boolean>;
  currentUserData: BehaviorSubject<string>;

  constructor(
    private http: HttpClient,
    private router: Router,
    private fb: FormBuilder,
    private loginService: LoginService // Asigna el servicio aquí
  ) {
    this.createForm();
  }

  createForm() {
    this.loginForm = this.fb.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });
  }

  Inicio() {
    // Si la autenticación es exitosa, redirige al monitoreo.
    const formValues = this.loginForm.value;
    const loginRequest = formValues as AuthLoginRequest;
    this.loginService.login(loginRequest).subscribe({
      next: (userData) => {
        if (userData == null) {
          // Manejo de caso donde userData es null
        }
      },
      error: (errorData) => {
        Swal.fire({
          icon: 'error',
          title: 'Error',
          text: 'Usuario o contraseña incorrectos',
        }).then(() => {
          // Limpiar los inputs después de mostrar el mensaje de error
          this.loginForm.reset();
        });
        console.error('Error al iniciar sesión:', errorData);
      },
      complete: () => {
        console.log('Inicio de sesión completado.');
      }
    });
  }

  /* private handleError(error: HttpErrorResponse) {
    if (error.status === 0) {
      console.error('Se ha producido un error:', error);
    } else {
      console.error('Backend retornó el código de estado:', error.status);
    }
    return throwError('Algo falló. Por favor, inténtelo nuevamente.');
  } */
}

