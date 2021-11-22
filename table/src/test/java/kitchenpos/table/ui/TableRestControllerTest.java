package kitchenpos.table.ui;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collections;
import kitchenpos.TableFixture;
import kitchenpos.table.application.TableService;
import kitchenpos.table.application.dto.OrderTableResponse;
import kitchenpos.table.application.dto.OrderTableResponses;
import kitchenpos.table.domain.OrderTable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

@DisplayName("주문 테이블 api")
@WebMvcTest(TableRestController.class)
class TableRestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TableService tableService;

    @Test
    void create() throws Exception {
        final OrderTable orderTable = TableFixture.createOrderTable();
        final String content = objectMapper.writeValueAsString(TableFixture.createOrderTableRequest(orderTable));
        when(tableService.create(any())).thenReturn(new OrderTableResponse(orderTable));

        final MockHttpServletResponse response = mockMvc.perform(post("/api/tables")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        assertAll(
                () -> assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value()),
                () -> assertThat(response.getHeader("Location")).isEqualTo("/api/tables/" + orderTable.getId())
        );
    }

    @Test
    void list() throws Exception {
        when(tableService.list()).thenReturn(
                new OrderTableResponses(Collections.singletonList(TableFixture.createOrderTable()))
        );

        final MockHttpServletResponse response = mockMvc.perform(get("/api/tables"))
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void changeEmpty() throws Exception {
        final OrderTable orderTable = TableFixture.createOrderTable();
        final String content = objectMapper.writeValueAsString(TableFixture.createOrderTableRequest(orderTable));
        when(tableService.changeEmpty(any(), any())).thenReturn(new OrderTableResponse(orderTable));

        final MockHttpServletResponse response = mockMvc.perform(put("/api/tables/" + orderTable.getId() + "/empty")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void changeNumberOfGuests() throws Exception {
        final OrderTable orderTable = TableFixture.createOrderTable();
        final String content = objectMapper.writeValueAsString(TableFixture.createOrderTableRequest(orderTable));
        when(tableService.changeNumberOfGuests(any(), any())).thenReturn(new OrderTableResponse(orderTable));

        final MockHttpServletResponse response = mockMvc.perform(
                        put("/api/tables/" + orderTable.getId() + "/number-of-guests")
                                .content(content)
                                .contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }
}
