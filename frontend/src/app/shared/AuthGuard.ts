import { Injectable } from "@angular/core";
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from "@angular/router";
import { Observable } from "rxjs";

@Injectable({ providedIn: 'root' })
export class AuthGuard implements CanActivate {

    constructor(
        private router: Router
    ) { }

    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
        let storage = localStorage.getItem('fb_auth');
        if (storage) {
            // authorised so return true
            return true;
        } else {
            // not logged in so redirect to login page with the return url
            this.router.navigate(['/login']);
            return false;
        }
    }

}