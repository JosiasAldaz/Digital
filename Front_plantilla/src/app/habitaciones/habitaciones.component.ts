import { Component, OnInit } from '@angular/core';
import { HabitacionesService } from '../services/habitaciones.service'; // Asegúrate de importar el servicio
import { Habitaciones } from '../model/Habitaciones'; // Asegúrate de importar el modelo
import Swal from 'sweetalert2';
import { TiposService } from '../services/tipos.service';

@Component({
  selector: 'app-habitaciones',
  templateUrl: './habitaciones.component.html',
  styleUrls: ['./habitaciones.component.scss']
})
export class HabitacionesComponent implements OnInit {

tiposHabitacion: any[] = [];
  habitaciones: Habitaciones[] = [];
  isModalOpen = false;
  registroSeleccionado: any = {};
  constructor(private habitacionesService: HabitacionesService,private servicioTipos: TiposService) { }

  ngOnInit(): void {
    this.cargarHabitaciones();
  }

  closeModal() {
    this.isModalOpen = false; // Cierra la ventana modal
  }

  cargarHabitaciones(): void {
    this.habitacionesService.getAlertas().subscribe(
      (data: Habitaciones[]) => {
        console.log('Datos cargados:', data);
        this.habitaciones = data;

      },
      (error) => {
        console.error('Error al cargar las habitaciones', error);
      }
    );
  }

  editar(habitacion: any) {
    this.registroSeleccionado = { ...habitacion }; 
    this.isModalOpen = true;
    console.log('Registro seleccionado:', this.registroSeleccionado);
    this.cargarComboTipos();
    
  }

  ver(id: number): void {
    // Lógica para ver los detalles de una habitación
    console.log('Ver habitación con ID:', id);
  }

  eliminar(id: number): void {
    Swal.fire({
      title: '¿Estás seguro?',
      text: 'Una vez eliminada, ¡no podrás recuperar esta habitación!',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Sí, eliminar',
      cancelButtonText: 'Cancelar'
    }).then((result) => {
      if (result.isConfirmed) {
        console.log('El ID seleccionado es: ', id);
        this.habitacionesService.eliminar(id).subscribe(
          (data: Habitaciones) => {
                Swal.fire(
                  'Success',
                  'Se eliminó la habitación correctamente',
                  'success'
                );
            this.cargarHabitaciones();
          },
          (error) => {
            console.error('Error al eliminar la habitación', error);
          }
        );

      }
    });
    
  }

  guardarCambios() {
    this.habitacionesService.actualizar(this.registroSeleccionado).subscribe(
      (data: Habitaciones) => {
        Swal.fire({
          icon: 'success',
          title: 'Éxito',
          text: 'Se han guardado los cambios correctamente.',
        });
        this.cargarHabitaciones();
        this.isModalOpen = false;
      },
      (error) => {
        Swal.fire({
          icon: 'error',
          title: 'Error',
          text: 'Ocurrió un error al guardar los cambios.',
        });
        console.error('Error al guardar los cambios', error);
      }
    );
    }

    cargarComboTipos(){
      this.servicioTipos.listar().subscribe(
        (data) => {
          this.tiposHabitacion = data;
        },
        (error) => {
          console.error('Error al cargar los tipos de habitación', error);
        }
      );
    }
}

