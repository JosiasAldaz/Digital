import { Permisos } from "./Permisos";
import { RoleEnum } from "./RoleEnum";

export class RoleEntity{

    id_rol: number;
    roleEnum: RoleEnum;
    permisos: Set<Permisos>;
}