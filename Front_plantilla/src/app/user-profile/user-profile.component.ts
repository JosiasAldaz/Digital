import { Component, OnInit } from '@angular/core';
import { TiposService } from '../services/tipos.service';
import { tipos } from '../model/tipos';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.css']
})
export class UserProfileComponent implements OnInit {
tipos: tipos[];
registroSeleccionado: any = {};
isModalOpen = false;
  constructor(private tiposervicio:TiposService) { }

  ngOnInit(): void {
    this.cargarTipos();
  }

  // Cierra la ventana modal
  closeModal() {
    this.isModalOpen = false; 
  }

  //CARGA LA LISTA DE TIPOS DE HABITACIONES
  cargarTipos(): void {
    this.tiposervicio.getAlertas().subscribe(
      (data: tipos[]) => {
        console.log('Datos cargados:', data);
        this.tipos = data;

      },
      (error) => {
        console.error('Error al cargar las habitaciones', error);
      }
    );
  }

  //MUESTRA EL MODAL PARA EDITAR
  editar(tipo: any) {
    this.registroSeleccionado = { ...tipo }; 
    this.isModalOpen = true;
    console.log('Registro seleccionado:', this.registroSeleccionado);
    
  }

  //ELIMINA EL TIPO DE HABITACION
  eliminar(id_number: number): void {
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
        // Llama al servicio de eliminación
        console.log('El ID seleccionado es: ', id_number);
        this.tiposervicio.buscar(id_number).subscribe(
          (data: tipos) => {
            if(data.habitaciones){
              if(data.habitaciones.length > 0){
                Swal.fire(
                  'Error',
                  'No se puede eliminar el tipo de habitación porque esta relacionado',
                  'error'
                );
              }else{
                this.tiposervicio.eliminar(id_number).subscribe(
                  () => {
                    Swal.fire(
                      'Eliminado',
                      'La habitación ha sido eliminada.',
                      'success'
                    );
                    // Opcional: Actualizar la lista de habitaciones
                    this.ngOnInit(); // Recarga los datos si es necesario
                  },
                  (error) => {
                    Swal.fire(
                      'Error',
                      'Ocurrió un error al eliminar la habitación.',
                      'error'
                    );
                    console.error('Error al eliminar la habitación', error);
                  }
                );
              }
            }
            
          },
          (error) => {
            console.error('Error al buscar la habitación', error);
          }
        );
      }
    });
  }

  guardarCambios() {
    this.tiposervicio.actualizar(this.registroSeleccionado).subscribe(
      (data: tipos) => {
        Swal.fire({
          icon: 'success',
          title: 'Éxito',
          text: 'Se han guardado los cambios correctamente.',
        });
        this.cargarTipos();
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
  

}
