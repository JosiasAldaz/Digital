import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { TiposService } from '../services/tipos.service';
import { HabitacionesService } from '../services/habitaciones.service';
import { Habitaciones } from '../model/Habitaciones';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-icons',
  templateUrl: './icons.component.html',
  styleUrls: ['./icons.component.css']
})
export class IconsComponent implements OnInit {
  tiposHabitacion: any[] = [];
  habitacion: Habitaciones = new Habitaciones();

  numeroHabitacion: string = '';
  tipoHabitacion: string = '';
  descripcion: string = '';
  precio: string = '';
  estado: string = '';

  constructor(
    private router: Router,
    private servicioTipos: TiposService,
    private habiServicio: HabitacionesService
  ) {}

  ngOnInit(): void {
    this.servicioTipos.listar().subscribe(
      (data) => {
        this.tiposHabitacion = data;
      },
      (error) => {
        console.error('Error al cargar los tipos de habitación', error);
      }
    );
  }

  Registrar() {
    // Convertir todos los campos a cadena y limpiar espacios
    const numeroHabitacion = this.numeroHabitacion.trim();
    const tipoHabitacion = this.tipoHabitacion.trim();
    const descripcion = this.descripcion.trim();
    const precio = String(this.precio || '').trim();  // Convertir `this.precio` a cadena
    const estado = this.estado.trim();
    console.log('Numero de habitacion: ',+numeroHabitacion);
  
    // Validar campos
    if (numeroHabitacion === '' || tipoHabitacion === '' || descripcion === '' || precio === '' || estado === '') {
      Swal.fire({
        icon: 'error',
        title: 'Error',
        text: 'Los campos están vacíos, por favor completa todos los campos.',
      });
      return;
    }
  
    const tipoHabitacionNumber = +tipoHabitacion; // Convertir a número
    const precioNumber = +precio; // Convertir a número
    console.log('Precio de habitacion: ',+precioNumber);
    if (isNaN(tipoHabitacionNumber) || tipoHabitacionNumber <= 0) {
      Swal.fire({
        icon: 'error',
        title: 'Error',
        text: 'Tipo de habitación no válido.',
      });
      return;
    }
  
    if (isNaN(precioNumber) || precioNumber < 0) {
      Swal.fire({
        icon: 'error',
        title: 'Error',
        text: 'Precio no válido.',
      });
      return;
    }
  
    this.servicioTipos.buscar(tipoHabitacionNumber).subscribe(
      (tipo) => {
        this.habitacion.numeroHabitacion = +numeroHabitacion;
        this.habitacion.descripcion = descripcion;
        this.habitacion.estado = estado;
        this.habitacion.precioHabitacion = precioNumber;
        this.habitacion.tipo = tipo;
  
        this.habiServicio.crear(this.habitacion).subscribe(
          (response) => {
            Swal.fire({
              icon: 'success',
              title: 'Éxito',
              text: 'La habitación ha sido registrada correctamente.',
            });
            this.router.navigate(['/ruta-al-listado']);
          },
          (error) => {
            Swal.fire({
              icon: 'error',
              title: 'Error',
              text: 'Ocurrió un error al registrar la habitación.',
            });
            console.error('Error al guardar la habitación', error);
          }
        );
      },
      (error) => {
        Swal.fire({
          icon: 'error',
          title: 'Error',
          text: 'Ocurrió un error al buscar el tipo de habitación.',
        });
        console.error('Error al buscar el tipo de habitación', error);
      }
    );
  }
  
  
  
  
}