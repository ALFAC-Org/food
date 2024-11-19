package br.com.alfac.food.infra.utils;

import java.util.UUID;

import br.com.alfac.food.core.application.cliente.dto.ClienteDTO;
import br.com.alfac.food.core.domain.cliente.Cliente;
import br.com.alfac.food.core.domain.cliente.vo.CPF;

public class ClienteHelper {
    public static ClienteDTO criarClienteDTO() {
        return criarClienteDTO(null, null, null);
    }

    public static ClienteDTO criarClienteDTO(String cpf, String nome, String email) {
        ClienteDTO clienteDto = new ClienteDTO();

        clienteDto.setCpf(new CPF(cpf != null && !cpf.isEmpty() ? cpf : "12345678901").getNumero());
        clienteDto.setNome(nome != null && !nome.isEmpty() ? nome : "John Doe");
        clienteDto.setEmail(email != null && !email.isEmpty() ? email : "john@doe.com");

        return clienteDto;
    }

    public static Cliente criarCliente() {
        return criarCliente(null, null, null);
    }

    public static Cliente criarCliente(String cpf, String nome, String email) {
        Cliente cliente = new Cliente();
        long id = 123;

        cliente.setId(id);
        cliente.setUuid(UUID.randomUUID());
        cliente.setCpf(new CPF(cpf != null && !cpf.isEmpty() ? cpf : "12345678901"));
        cliente.setNome(nome != null && !nome.isEmpty() ? nome : "John Doe");
        cliente.setEmail(email != null && !email.isEmpty() ? email : "john@doe.com");

        return cliente;
    }
}
