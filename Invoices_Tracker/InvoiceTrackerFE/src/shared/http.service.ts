import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {API_CONST} from './shared.constants';
@Injectable({
  providedIn: 'root'
})
export class HttpService {

  constructor(private httpClient: HttpClient) {
  }

  public get(action: string) {
    return this.httpClient.get(`${API_CONST.BASE_URL}${action}`);
  }

  public post(action: string, data: any) {
    return this.httpClient.post(`${API_CONST.BASE_URL}${action}`, data);
  }

  public put(action: string, data: any) {
    return this.httpClient.put(`${API_CONST.BASE_URL}${action}`, data);
  }
  public delete(action: string) {
    return this.httpClient.delete(`${API_CONST.BASE_URL}${action}`);
  }
}