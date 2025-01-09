# Clínica Psicológica Vida Estável
![image](https://github.com/user-attachments/assets/7f150f33-5b1f-4741-b80c-359a6dac7cce)


## Descrição

O projeto **Clínica Psicológica - Vida Estável** é uma aplicação desenvolvida para gerenciar as atividades de uma clínica de psicologia. O sistema permite o agendamento de consultas e o gerenciamento de pacientes e profissionais.

## Funcionalidades

- **Cadastro de Pacientes**: Permite o registro de informações dos pacientes, incluindo dados pessoais.
- **Agendamento de Consultas**: Facilita o agendamento de consultas entre pacientes e psicólogos, com opções de visualização de horários disponíveis, busca por especialidade médica e etc.
- **Gerenciamento de Profissionais**: Cadastro e gerenciamento de psicólogos, incluindo especializações, horários de atendimento.
- **Relatórios**: Geração de relatórios sobre atendimentos realizados, pacientes ativos e estatísticas de agendamentos.

## Tecnologias Utilizadas

- **Backend**: Java, Spring Boot, Spring Security, Flyway, Swagger, Docker.
- **Banco de Dados**: PostgreSQL.
- **Outras Tecnologias**: JacksonDataFormat, Hateoas, Auth0.

## Instalação

Para rodar o código, certifique-se que o seu Docker está corretamente instalado na máquina.
1.  Clone o repositório: 
git clone https://github.com/Iago-on-github/Clinica_Psicologa_Vida_Est-vel.git

2. Navegue até o diretório:
cd Clinica_Psicologa_Vida_Est-vel

3. Construa a imagem Docker:
docker build -t <nome-da-imagem> .

4. Inicie o contâiner:
docker run -d -p 8080:8080 <nome-da-imagem>

5. Acesse a aplicação:
Abra seu navegador e vá para http://localhost:8080 para acessar a aplicação.
