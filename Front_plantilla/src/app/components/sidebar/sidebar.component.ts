import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LoginService } from '../../services/login.service';
import Swal from 'sweetalert2';

declare interface RouteInfo {
    path: string;
    title: string;
    icon: string;
    class: string;
}
export const ROUTES: RouteInfo[] = [
    { path: '/habitaciones', title: 'HABITACIONES',  icon: 'design-2_ruler-pencil', class: '' },
    { path: '/user-profile', title: 'TIPOS DE HABITACIONES',  icon:'users_single-02', class: '' },
    { path: '/icons', title: 'REGISTRO DE HABITACIONES',  icon:'education_atom', class: '' },
    { path: '/maps', title: 'REGISTRO DE TIPOS',  icon:'location_map-big', class: '' },
    { path: '', title: 'Log out',  icon:'objects_spaceship', class: '' } // Dejar el path vacío para logout
];

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.css']
})
export class SidebarComponent implements OnInit {
  menuItems: any[];
  //private loginService: LoginService;

  constructor(private router: Router,private loginService: LoginService) { }

  ngOnInit() {
    this.menuItems = ROUTES.filter(menuItem => menuItem);
  }

  isMobileMenu() {
    return window.innerWidth <= 991;
  }

  logout() {
    this.loginService.logout();
    Swal.fire({
      icon: 'success',
      title: 'Logout',
      text: 'Se ha cerrado sesión correctamente',
    }).then(() => {
      this.router.navigate(['/login']); // Redirige al usuario a la página de login
    });
  }
}


