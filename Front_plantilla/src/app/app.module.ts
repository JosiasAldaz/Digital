import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms'; // <-- Importa ReactiveFormsModule aquí
import { HttpClientModule } from '@angular/common/http';
import { RouterModule } from '@angular/router';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { ToastrModule } from 'ngx-toastr';
import { AppRoutingModule } from './app.routing';
import { ComponentsModule } from './components/components.module';
import { AppComponent } from './app.component';
import { AdminLayoutComponent } from './layouts/admin-layout/admin-layout.component';
import { LoginComponent } from './login/login.component';
import { LoginService } from './services/login.service';
import { TiposService } from './services/tipos.service';
import { HabitacionesService } from './services/habitaciones.service';
import { HabitacionesComponent } from './habitaciones/habitaciones.component';


@NgModule({
  imports: [
    BrowserAnimationsModule,
    FormsModule,
    ReactiveFormsModule, // <-- Añade ReactiveFormsModule aquí
    HttpClientModule,
    ComponentsModule,
    RouterModule,
    AppRoutingModule,
    NgbModule,
    ToastrModule.forRoot()
  ],
  declarations: [
    AppComponent,
    AdminLayoutComponent,
    LoginComponent,
    HabitacionesComponent,
  ],
  providers: [LoginService,TiposService,HabitacionesService],
  bootstrap: [AppComponent]
})
export class AppModule { }

