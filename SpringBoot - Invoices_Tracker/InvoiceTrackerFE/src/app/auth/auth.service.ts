import {Injectable} from '@angular/core';
import {HttpService} from '../../shared/http.service';
import {API_CONST, ROUTES, STORAGE} from '../../shared/shared.constants';
import {BehaviorSubject, Observable} from 'rxjs';
import {User} from 'src/shared/models/user.model';
import {Router} from '@angular/router';
import {Login} from './login/login.model';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  
  // token: string | undefined = localStorage.getItem(STORAGE.TOKEN);
  // userSubject: BehaviorSubject<User> = new BehaviorSubject<User>(JSON.parse(localStorage.getItem(STORAGE.USER)));
  // user: User | undefined;

  constructor(private http: HttpService, private router: Router) {
  }

  // login(payload: Login): Observable<any> {
  //   return this.http.post(API_CONST.ACTIONS.LOGIN, payload);
  // }
  // saveUser(user: User){
  //   this.user = user;
  //   localStorage.setItem(STORAGE.USER, JSON.stringify(user));
  //   this.userSubject.next(user);
  // }
  // routeUser(){
  //   this.router.navigate([ROUTES.DASHBOARD]);
  // }
  // getUserInformation() {
  //   return this.http.get(API_CONST.ACTIONS.USER_INFO);
  // }


  // isLoggedIn(): boolean {
  //   return !!localStorage.getItem(STORAGE.TOKEN);
  // }

  // storeToken(token: {token: string}) {
  //   this.token = token.token;
  //   localStorage.setItem(STORAGE.TOKEN, token.token);
  // }
  // getUser(): User{
  //   return this.userSubject.getValue();
  // }
  // getToken(): string {
  //   return this.token;
  // }

  // logout() {
  //   this.token = null;
  //   localStorage.removeItem(STORAGE.USER);
  //   localStorage.removeItem(STORAGE.TOKEN);
  //   this.userSubject.next(null);
  //   this.user = null;
  //   this.router.navigate([ROUTES.LOGIN]);
  // }
}