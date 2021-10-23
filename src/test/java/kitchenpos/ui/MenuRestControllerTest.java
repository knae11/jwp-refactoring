package kitchenpos.ui;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collections;
import kitchenpos.application.MenuService;
import kitchenpos.domain.Menu;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(MenuRestController.class)
class MenuRestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MenuService menuService;

    @Test
    void create() throws Exception {
        final Menu menuDto = new Menu();
        final String content = objectMapper.writeValueAsString(menuDto);
        final Menu menu = new Menu();
        menu.setId(1L);
        when(menuService.create(any())).thenReturn(menu);

        final MockHttpServletResponse response = mockMvc.perform(post("/api/menus")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        assertAll(
                () -> assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value()),
                () -> assertThat(response.getHeader("Location")).isEqualTo("/api/menus/1")
        );
    }

    @Test
    void list() throws Exception {
        when(menuService.list()).thenReturn(Collections.singletonList(new Menu()));

        final MockHttpServletResponse response = mockMvc.perform(get("/api/menus"))
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }
}
