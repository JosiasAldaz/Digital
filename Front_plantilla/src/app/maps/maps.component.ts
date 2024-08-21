import { Component } from '@angular/core';
import { TiposService } from '../services/tipos.service';
import { tipos } from '../model/tipos';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-maps',
  templateUrl: './maps.component.html',
  styleUrls: ['./maps.component.css']
})
export class MapsComponent {
  tipo: string = '';

  constructor(private servicioTipos: TiposService) {}

  Registrar(form: any): void {
    if (form.invalid) {
      Swal.fire({
        icon: 'error',
        title: 'Error',
        text: 'Los campos están vacíos, por favor completa todos los campos.',
      });
      return;
    }
    const nuevoTipo: tipos = {
      nombre: this.tipo,
      
    };
    this.servicioTipos.crear(nuevoTipo).subscribe(
      response => {
        Swal.fire({
            icon: 'success',
            title: 'Creación correcta',
            text: 'tipo '+this.tipo+' correctamente',
        });
        form.reset(); 
      },
      error => {
        console.error('Error al registrar tipo', error);
      }
    );
  }
}
