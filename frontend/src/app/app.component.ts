import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ConsultaCreditosComponent } from './components/consulta-creditos/consulta-creditos.component';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
  standalone: true,
  imports: [CommonModule, ConsultaCreditosComponent]
})
export class AppComponent {
  title = 'Consulta de Créditos';
}
