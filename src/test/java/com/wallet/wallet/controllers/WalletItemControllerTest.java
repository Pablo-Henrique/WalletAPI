package com.wallet.wallet.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wallet.wallet.dtos.WalletItemDto;
import com.wallet.wallet.enums.TypeEnum;
import com.wallet.wallet.models.User;
import com.wallet.wallet.models.UserWallet;
import com.wallet.wallet.models.Wallet;
import com.wallet.wallet.models.WalletItem;
import com.wallet.wallet.services.UserService;
import com.wallet.wallet.services.UserWalletService;
import com.wallet.wallet.services.WalletItemService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(profiles = "test")
public class WalletItemControllerTest {

    @MockBean
    private WalletItemService walletItemService;

    @MockBean
    private UserWalletService userWalletService;

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    private static final Long ID = 1L;
    private static final Date DATE = new Date();
    private static final LocalDate TODAY = LocalDate.now();
    private static final TypeEnum TYPE = TypeEnum.EN;
    private static final String DESCRIPTION = "Conta de Luz";
    private static final BigDecimal VALUE = BigDecimal.valueOf(65);
    private static final String URL = "/wallet-item";

    @Test
    @WithMockUser
    public void testSave() throws Exception {

        BDDMockito.given(walletItemService.save(Mockito.any(WalletItem.class))).willReturn(mockWalletItem());
        mockMvc.perform(MockMvcRequestBuilders.post(URL).content(jsonPayLoad())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.id").value(ID))
                .andExpect(jsonPath("$.data.date").value(TODAY.format(dateFormatter())))
                .andExpect(jsonPath("$.data.description").value(DESCRIPTION))
                .andExpect(jsonPath("$.data.type").value(TYPE.getValue()))
                .andExpect(jsonPath("$.data.value").value(VALUE))
                .andExpect(jsonPath("$.data.wallet").value(ID));
    }

    @Test
    @WithMockUser
    public void testFindBetweenDates() throws Exception {
        List<WalletItem> list = new ArrayList<>();
        list.add(mockWalletItem());
        Page<WalletItem> page = new PageImpl(list);

        String startDate = TODAY.format(dateFormatter());
        String endDate = TODAY.plusDays(5).format(dateFormatter());

        User user = new User();
        user.setId(1L);

        BDDMockito.given(walletItemService.findBetweenDates(Mockito.anyLong(), Mockito.any(Date.class), Mockito.any(Date.class), Mockito.anyInt())).willReturn(page);
        BDDMockito.given(userService.findByEmail(Mockito.anyString())).willReturn(Optional.of(user));
        BDDMockito.given(userWalletService.findByUserIdAndWalletId(Mockito.anyLong(), Mockito.anyLong())).willReturn(Optional.of(new UserWallet()));

        mockMvc.perform(MockMvcRequestBuilders.get(URL + "/1?startDate=" + startDate + "&endDate=" + endDate)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.content[0].id").value(ID))
                .andExpect(jsonPath("$.data.content[0].date").value(TODAY.format(dateFormatter())))
                .andExpect(jsonPath("$.data.content[0].description").value(DESCRIPTION))
                .andExpect(jsonPath("$.data.content[0].type").value(TYPE.getValue()))
                .andExpect(jsonPath("$.data.content[0].value").value(VALUE))
                .andExpect(jsonPath("$.data.content[0].wallet").value(ID));    }

    @Test
    @WithMockUser
    public void testFindByType() throws Exception {
        List<WalletItem> items = new ArrayList<>();
        items.add(mockWalletItem());

        BDDMockito.given(walletItemService.findByWalletAndType(Mockito.anyLong(), Mockito.any(TypeEnum.class))).willReturn(items);
        mockMvc.perform(MockMvcRequestBuilders.get(URL + "/type/1?type=ENTRADA")
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.[0].id").value(ID))
                .andExpect(jsonPath("$.data.[0].date").value(TODAY.format(dateFormatter())))
                .andExpect(jsonPath("$.data.[0].description").value(DESCRIPTION))
                .andExpect(jsonPath("$.data.[0].type").value(TYPE.getValue()))
                .andExpect(jsonPath("$.data.[0].value").value(VALUE))
                .andExpect(jsonPath("$.data.[0].wallet").value(ID));
    }

    @Test
    @WithMockUser
    public void testSumByWallet() throws Exception {
        BigDecimal value = BigDecimal.valueOf(536.90);
        BDDMockito.given(walletItemService.sumByWalletId(Mockito.anyLong())).willReturn(value);
        mockMvc.perform(MockMvcRequestBuilders.get(URL + "/total/1")
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").value(536.90));
    }

    @Test
    @WithMockUser
    public void testUpdate() throws Exception {
        String description = "Nova descrição";
        Wallet wallet = new Wallet();
        wallet.setId(ID);

        BDDMockito.given(walletItemService.findById(Mockito.anyLong())).willReturn(Optional.of(mockWalletItem()));
        BDDMockito.given(walletItemService.save(Mockito.any(WalletItem.class))).willReturn(new WalletItem(ID, DATE, TypeEnum.SD, description, VALUE, wallet));

        mockMvc.perform(MockMvcRequestBuilders.put(URL).content(jsonPayLoad())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(ID))
                .andExpect(jsonPath("$.data.date").value(TODAY.format(dateFormatter())))
                .andExpect(jsonPath("$.data.description").value(description))
                .andExpect(jsonPath("$.data.type").value(TypeEnum.SD.getValue()))
                .andExpect(jsonPath("$.data.value").value(VALUE))
                .andExpect(jsonPath("$.data.wallet").value(ID));
    }

    @Test
    @WithMockUser
    public void testUpdateWalletChance() throws Exception {
        Wallet wallet = new Wallet();
        wallet.setId(99L);

        WalletItem wi = new WalletItem(ID, DATE, TypeEnum.SD, DESCRIPTION, VALUE, wallet);

        BDDMockito.given(walletItemService.findById(Mockito.anyLong())).willReturn(Optional.of(wi));

        mockMvc.perform(MockMvcRequestBuilders.put(URL).content(jsonPayLoad())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.data").doesNotExist())
                .andExpect(jsonPath("$.errors[0]").value("Você não pode alterar a carteira"));
    }

    @Test
    @WithMockUser
    public void testUpdateInvalidId() throws Exception {

        BDDMockito.given(walletItemService.findById(Mockito.anyLong())).willReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.put(URL).content(jsonPayLoad())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.data").doesNotExist())
                .andExpect(jsonPath("$.errors[0]").value("WalletItem nao encontrado"));
    }

    @Test
    @WithMockUser
    public void testDelete() throws Exception {

        BDDMockito.given(walletItemService.findById(Mockito.anyLong())).willReturn(Optional.of(new WalletItem()));

        mockMvc.perform(MockMvcRequestBuilders.delete(URL + "/1")
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").value("WalletItem de id "+ ID + " apagada com sucesso"));
    }

    @Test
    @WithMockUser
    public void testDeleteInvalidId() throws Exception {

        BDDMockito.given(walletItemService.findById(Mockito.anyLong())).willReturn(Optional.empty());
        mockMvc.perform(MockMvcRequestBuilders.delete(URL + "/99")
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.data").doesNotExist())
                .andExpect(jsonPath("$.errors[0]").value("WalletItem de id "+ 99 + " nao encontrada"));
    }

    private WalletItem mockWalletItem() {
        Wallet wallet = new Wallet();
        wallet.setId(1L);
        return new WalletItem(ID, DATE, TYPE, DESCRIPTION, VALUE, wallet);
    }

    private String jsonPayLoad() throws JsonProcessingException {
        WalletItemDto dto = new WalletItemDto();
        dto.setId(ID);
        dto.setDate(DATE);
        dto.setDescription(DESCRIPTION);
        dto.setType(TYPE.getValue());
        dto.setValue(VALUE);
        dto.setWallet(ID);

        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(dto);
    }

    private DateTimeFormatter dateFormatter() {
        return DateTimeFormatter.ofPattern("dd-MM-yyyy");
    }

}
