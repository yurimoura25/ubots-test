# Instruções para Executar o Projeto

Para rodar este projeto, siga as instruções abaixo. Certifique-se de ter o Docker instalado em sua máquina.

## Passos para Executar o Projeto

1. **Download do Projeto:**
    - Baixe o projeto do repositório Git ou extraia o arquivo compactado.

2. **Configuração do Ambiente:**
    - Certifique-se de ter o Docker instalado em sua máquina.

3. **Execução do Projeto:**
    - Abra um terminal na pasta raiz do projeto.

4. **Comando Docker:**
    - Execute o seguinte comando para iniciar o projeto:
      ```bash
      docker-compose up -d
      ```

5. **Acesso ao Projeto:**
    - Após a execução bem-sucedida, o projeto estará rodando na porta 8080 e pronto para ser utilizado.

6. **Testando as Funcionalidades:**
    - Utilize as rotas disponíveis conforme descrito no README para salvar atendentes, enviar solicitações de atendimento e receber informações de atendimento.

7. **Finalização do Projeto:**
    - Para parar a execução do projeto, utilize o comando:
      ```bash
      docker-compose down
      ```