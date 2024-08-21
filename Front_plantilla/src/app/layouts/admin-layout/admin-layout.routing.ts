import { Routes } from '@angular/router';

import { UserProfileComponent } from '../../user-profile/user-profile.component';
import { IconsComponent } from '../../icons/icons.component';
import { MapsComponent } from '../../maps/maps.component';
import { LoginComponent } from '../../login/login.component';
import { HabitacionesComponent } from '../../habitaciones/habitaciones.component';

export const AdminLayoutRoutes: Routes = [
    { path: 'user-profile',   component: UserProfileComponent },
    { path:'habitaciones', component:HabitacionesComponent},
    { path: 'icons',          component: IconsComponent },
    { path: 'maps',           component: MapsComponent },
    {path: 'login',        component: LoginComponent},
];
