import { RoleEntity } from "./RoleEntity";

export class Usuario {
    id_usuario: number;
    username: string;
    password: string;
    cargo: string;
    isEnabled: boolean;
    accountNonExpired:boolean;
    accountNonLocked:boolean;
    credentialsNonExpired:boolean;
    roles :Set<RoleEntity>;
}
