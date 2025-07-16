import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Credito {
  id: number;
  numeroCredito: string;
  numeroNfse: string;
  dataConstituicao: string;
  valorIssqn: number;
  tipoCredito: string;
  simplesNacional: boolean;
  aliquota: number;
  valorFaturado: number;
  valorDeducao: number;
  baseCalculo: number;
}

@Injectable({
  providedIn: 'root'
})
export class CreditoService {
  private apiUrl = 'http://localhost:8080/api/creditos';

  constructor(private http: HttpClient) {}

  buscarPorNfse(nfse: string): Observable<Credito[]> {
    return this.http.get<Credito[]>(`${this.apiUrl}/${nfse}`);
  }

  buscarPorNumeroCredito(numero: string): Observable<Credito> {
    return this.http.get<Credito>(`${this.apiUrl}/numero/${numero}`);
  }
}