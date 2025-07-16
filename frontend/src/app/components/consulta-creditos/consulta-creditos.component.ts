import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { CreditoService, Credito } from '../../services/credito.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-consulta-creditos',
  templateUrl: './consulta-creditos.component.html',
  standalone: true,
  imports: [CommonModule, FormsModule]
})
export class ConsultaCreditosComponent {
  termoBusca = '';
  tipoBusca: 'nfse' | 'numero' = 'nfse';
  creditos: Credito[] = [];
  error = '';
  mensagem = '';

  constructor(private creditoService: CreditoService) {}

  buscar() {
    if (!this.termoBusca.trim()) {
      this.mensagem = 'Por favor, digite um termo para busca.';
      return;
    }

    Swal.fire({
      title: 'Buscando créditos',
      text: 'Aguarde um momento...',
      allowOutsideClick: false,
      allowEscapeKey: false,
      didOpen: () => {
        Swal.showLoading();
      }
    });
    this.error = '';
    this.mensagem = '';
    this.creditos = [];

    if (this.tipoBusca === 'nfse') {
      this.buscarPorNfse();
    } else {
      this.buscarPorNumero();
    }
  }

  private buscarPorNfse() {
    this.creditoService.buscarPorNfse(this.termoBusca).subscribe({
      next: (data) => {
        this.creditos = data;
        Swal.close();
        if (data.length === 0) {
          this.mensagem = 'Nenhum crédito encontrado para esta NFSE.';
        }
      },
      error: (err) => {
        Swal.close();
        Swal.fire({
          title: 'Erro',
          text: 'Erro ao buscar créditos',
          icon: 'error',
        });
      }
    });
  }

  private buscarPorNumero() {
    this.creditoService.buscarPorNumeroCredito(this.termoBusca).subscribe({
      next: (data) => {
        Swal.close();
        if (data && Object.keys(data).length > 0) {
          this.creditos = [data]; // Converte para array para usar a mesma tabela
        } else {
          this.creditos = [];
          this.mensagem = 'Crédito não encontrado.';
        }
      },
      error: (err) => {
        Swal.close();
        Swal.fire({
          title: 'Erro',
          text: 'Erro ao buscar créditos',
          icon: 'error',
        });
      }
    });
  }

  limparBusca() {
    this.termoBusca = '';
    this.creditos = [];
    this.mensagem = '';
  }

  formatarData(data: string): string {
    return new Date(data).toLocaleDateString('pt-BR');
  }

  formatarMoeda(valor: number): string {
    return new Intl.NumberFormat('pt-BR', {
      style: 'currency',
      currency: 'BRL'
    }).format(valor);
  }
}