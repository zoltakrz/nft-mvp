import { Component } from '@angular/core';
import { OpenAIService } from './open-ai.service';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-root',
  standalone:true,
  templateUrl: './app.component.html',
  styleUrl: './app.component.css',
  imports:
    [
      FormsModule
    ]
})
export class AppComponent {
  title = 'nft_ai_generator';

  textPrompt !: string;
  imagePrompt !: string;

  constructor(private openAIService: OpenAIService) { }

  async sendTextPrompt() {
      console.log('start sending');
      console.log('text prompt ' + this.textPrompt);
      const response = await this.openAIService.queryModelText(this.textPrompt);
      console.log(response);
  }

  async sendImagePrompt() {
    console.log('start sending');
    const response = await this.openAIService.queryModelImage(this.imagePrompt);
  }

}
