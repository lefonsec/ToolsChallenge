package com.toolschallenge.prova.controller;

import com.toolschallenge.prova.service.PagamentoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class PagamentoControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PagamentoService pagamentoService;

    @InjectMocks
    private PagamentoController pagamentoController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(pagamentoController).build();
    }

    @Test
    void testRealizarPagamento() throws Exception {
        String requestBody = buildJsonRequestBody();
        mockMvc.perform(post("/api/pagamento").contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated());
    }

    @Test
    void testEstornarPagamento() throws Exception {
        String id = "1";
        mockMvc.perform(post("/api/estorno").param("id", id))
                .andExpect(status().isCreated());
    }

    @Test
    void testConsultarPagamentoPorID() throws Exception {
        String id = "1";
        mockMvc.perform(get("/api/consulta/{id}", id)).andExpect(status().isOk());
    }

    @Test
    void testConsultarTodosPagamentos() throws Exception {
        mockMvc.perform(get("/api/transacoes")).andExpect(status().isOk());
    }
    public static String buildJsonRequestBody() {
        return "{\n" +
                "\t\"transacao\": {\n" +
                "\t\t\"cartao\": \"4\",\n" +
                "\t\t\"id\": \"3\",\n" +
                "\t\t\"descricao\": {\n" +
                "\t\t\t\"valor\": 15,\n" +
                "\t\t\t\"dataHora\": \"01/05/2023 18:30:00\",\n" +
                "\t\t\t\"estabelecimento\": \"PetShop Mundo Cão\"\n" +
                "\t\t},\n" +
                "\t\t\"formaPagamento\": {\n" +
                "\t\t\t\"tipo\": \"PARCELADO_EMISSOR\",\n" +
                "\t\t\t\"parcela\": \"2\"\n" +
                "\t\t}\n" +
                "\t}\n" +
                "}";
    }
}
