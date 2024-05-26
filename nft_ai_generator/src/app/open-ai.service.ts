import { Injectable } from '@angular/core';
import { OpenAIClient, AzureKeyCredential } from '@azure/openai';


@Injectable({
  providedIn: 'root'
})
export class OpenAIService {

  constructor() { }

    async queryModelText(prompt:string) {
      const secretKey : string = 'cc72084b32754d5fb77f2ec89834ba5b';
      const resource : string = 'https://nftgeneratorai.openai.azure.com/';
      const client = new OpenAIClient(resource,new AzureKeyCredential(secretKey));

      const { choices } = await client.getCompletions(
        "gpt35turboDeployment", // assumes a matching model deployment or model name
        [prompt]);

      console.log('finish asking')
      return choices;
    
  }

  async queryModelImage(prompt:string) {
    const secretKey : string = 'cc72084b32754d5fb77f2ec89834ba5b';
    const resource : string = 'https://nftgeneratorai.openai.azure.com/';
    const client = new OpenAIClient(resource,new AzureKeyCredential(secretKey));

    const deploymentName = "dalleDeployment";
    const size = "1024x1024";
    const n = 1;
    
    const results = await client.getImages(deploymentName, prompt, { n, size });
  
    for (const image of results.data) {
      console.log(`Image generation result URL: ${image.url}`);
      return image.url;
    }
    return ;
}
}




