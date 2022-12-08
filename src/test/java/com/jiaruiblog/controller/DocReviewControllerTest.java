package com.jiaruiblog.controller;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.jiaruiblog.DocumentSharingSiteApplication;
import com.jiaruiblog.common.MessageConstant;
import com.jiaruiblog.entity.dto.BatchIdDTO;
import com.jiaruiblog.entity.dto.RefuseDTO;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DocumentSharingSiteApplication.class)
public class DocReviewControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setUp(){
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @After
    public void tearDown(){
    }

    /**
     * @Author luojiarui
     * @Description 仅仅有管理员可以进行评审
     * 正常参数
     * @Date 22:37 2022/12/6
     * @Param []
     **/
    @Test
    public void queryDocReviewListTest1() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .get("/docReview/queryDocForReview")
                .requestAttr("id", "1")
                .param("page", String.valueOf(1))
                .param("rows", String.valueOf(10))
                .contentType(MediaType.APPLICATION_JSON)
                // 设置返回值类型为utf-8，否则默认为ISO-8859-1
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String result1 = result.getResponse().getContentAsString(Charset.defaultCharset());
        Assert.assertEquals(200, JSON.parseObject(result1).get("code"));
    }

    /**
     * @Author luojiarui
     * @Description 仅仅有管理员可以进行评审, page为负数
     * @Date 22:37 2022/12/6
     * @Param []
     **/
    @Test
    public void queryDocReviewListTest2() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .get("/docReview/queryDocForReview")
                .requestAttr("id", "1")
                .param("rows", String.valueOf(10))
                .contentType(MediaType.APPLICATION_JSON)
                // 设置返回值类型为utf-8，否则默认为ISO-8859-1
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String result1 = result.getResponse().getContentAsString(Charset.defaultCharset());
        Assert.assertEquals(MessageConstant.PARAMS_ERROR_CODE, JSON.parseObject(result1).get("code"));
        Assert.assertEquals(MessageConstant.PARAMS_IS_NOT_NULL, JSON.parseObject(result1).get("message"));
    }

    /**
     * @Author luojiarui
     * @Description 仅仅有管理员可以进行评审， page 超过限制
     * @Date 22:37 2022/12/6
     * @Param []
     **/
    @Test
    public void queryDocReviewListTest3() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .get("/docReview/queryDocForReview")
                .requestAttr("id", "1")
                .param("page", String.valueOf(1))
                .param("rows", String.valueOf(110))
                .contentType(MediaType.APPLICATION_JSON)
                // 设置返回值类型为utf-8，否则默认为ISO-8859-1
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();;
        String result1 = result.getResponse().getContentAsString(Charset.defaultCharset());
        Assert.assertEquals(MessageConstant.PARAMS_ERROR_CODE, JSON.parseObject(result1).get("code"));
        Assert.assertEquals(MessageConstant.PARAMS_FORMAT_ERROR, JSON.parseObject(result1).get("message"));
    }

    /**
     * @Author luojiarui
     * @Description 更新文档为已读状态
     * @Date 22:17 2022/12/8
     * @Param []
     * @return void
     **/
    @Test
    public void updateDocReviewTest1() throws Exception {
        List<String> ids = Lists.newArrayList("1", "2", "3");
        Map<String, List<String>> params = new HashMap<>();
        params.put("ids", ids);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .put("/docReview/userRead")
                // 设置返回值类型为utf-8，否则默认为ISO-8859-1
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSON.toJSONString(params))
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String result1 = result.getResponse().getContentAsString(Charset.defaultCharset());
        Assert.assertEquals(200, JSON.parseObject(result1).get("code"));
    }

    /**
     * @Author luojiarui
     * @Description 更新文档为已读状态，参数格式错误
     * @Date 22:17 2022/12/8
     * @Param []
     * @return void
     **/
    @Test
    public void updateDocReviewTest2() throws Exception {
        List<String> ids = Lists.newArrayList("1", "2", "3");
        Map<String, List<String>> params = new HashMap<>();
        params.put("ids", ids);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .put("/docReview/userRead")
                // 设置返回值类型为utf-8，否则默认为ISO-8859-1
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSON.toJSONString(ids))
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String result1 = result.getResponse().getContentAsString(Charset.defaultCharset());
        Assert.assertEquals(MessageConstant.PARAMS_ERROR_CODE, JSON.parseObject(result1).get("code"));
        Assert.assertEquals(MessageConstant.PARAMS_TYPE_ERROR, JSON.parseObject(result1).get("message"));
    }

    /**
     * @Author luojiarui
     * @Description 更新文档为已读状态，不具备ids的key值
     * @Date 22:17 2022/12/8
     * @Param []
     * @return void
     **/
    @Test
    public void updateDocReviewTest3() throws Exception {
        List<String> ids = Lists.newArrayList("1", "2", "3");
        Map<String, List<String>> params = new HashMap<>();
        params.put("ids1", ids);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .put("/docReview/userRead")
                // 设置返回值类型为utf-8，否则默认为ISO-8859-1
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSON.toJSONString(params))
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String result1 = result.getResponse().getContentAsString(Charset.defaultCharset());
        Assert.assertEquals(MessageConstant.PARAMS_ERROR_CODE, JSON.parseObject(result1).get("code"));
        Assert.assertEquals(MessageConstant.PARAMS_IS_NOT_NULL, JSON.parseObject(result1).get("message"));
    }

    /**
     * @Author luojiarui
     * @Description 更新文档为已读状态，传递的参数是空的
     * @Date 22:17 2022/12/8
     * @Param []
     * @return void
     **/
    @Test
    public void updateDocReviewTest4() throws Exception {
        List<String> ids = Lists.newArrayList();
        Map<String, List<String>> params = new HashMap<>();
        params.put("ids", ids);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .put("/docReview/userRead")
                // 设置返回值类型为utf-8，否则默认为ISO-8859-1
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSON.toJSONString(params))
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String result1 = result.getResponse().getContentAsString(Charset.defaultCharset());
        Assert.assertEquals(200, JSON.parseObject(result1).get("code"));
    }

    /**
     * @Author luojiarui
     * @Description 管理员拒绝文档
     * @Date 21:06 2022/12/8
     * @Param []
     * @return void
     **/
    @Test
    public void refuseTest1() throws Exception {
        RefuseDTO refuseDTO = new RefuseDTO();
        refuseDTO.setDocId("2112");
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .post("/docReview/refuse")
                // 设置返回值类型为utf-8，否则默认为ISO-8859-1
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSON.toJSONString(refuseDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String result1 = result.getResponse().getContentAsString(Charset.defaultCharset());
        System.out.println(result1);
    }

    /**
     * @Author luojiarui
     * @Description 管理员拒绝文档
     * @Date 21:06 2022/12/8
     * @Param []
     * @return void
     **/
    @Test
    public void refuseTest2() throws Exception {
        RefuseDTO refuseDTO = new RefuseDTO();
        refuseDTO.setDocId("2112");
        refuseDTO.setReason("slfdjslfjdsljfl");
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .post("/docReview/refuse")
                // 设置返回值类型为utf-8，否则默认为ISO-8859-1
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSON.toJSONString(refuseDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String result1 = result.getResponse().getContentAsString(Charset.defaultCharset());
        System.out.println(result1);
    }

    /**
     * @Author luojiarui
     * @Description 管理员拒绝文档
     * @Date 21:06 2022/12/8
     * @Param []
     * @return void
     **/
    @Test
    public void refuseTest3() throws Exception {
        RefuseDTO refuseDTO = new RefuseDTO();
        refuseDTO.setDocId("");
        refuseDTO.setReason("this is a reason");
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .post("/docReview/refuse")
                // 设置返回值类型为utf-8，否则默认为ISO-8859-1
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSON.toJSONString(refuseDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String result1 = result.getResponse().getContentAsString(Charset.defaultCharset());
        System.out.println(result1);
    }

    /**
     * @Author luojiarui
     * @Description 批量拒绝文档的上传
     * @Date 22:33 2022/12/8
     * @Param []
     * @return void
     **/
    @Test
    public void refuseBatchTest1() throws Exception {
        BatchIdDTO batchIdDTO = new BatchIdDTO();
        List<String> ids = Lists.newArrayList("a", "b", "c");
        batchIdDTO.setIds(ids);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .post("/docReview/refuseBatch")
                // 设置返回值类型为utf-8，否则默认为ISO-8859-1
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSON.toJSONString(batchIdDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String result1 = result.getResponse().getContentAsString(Charset.defaultCharset());
        System.out.println(result1);
    }

    /**
     * @Author luojiarui
     * @Description 批量拒绝文档的上传
     * 没有参数, 类型转换错误
     * @Date 22:33 2022/12/8
     * @Param []
     * @return void
     **/
    @Test
    public void refuseBatchTest2() throws Exception {
        BatchIdDTO batchIdDTO = new BatchIdDTO();
        List<String> ids = Lists.newArrayList("a", "b", "c");
        batchIdDTO.setIds(ids);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .post("/docReview/refuseBatch")
                // 设置返回值类型为utf-8，否则默认为ISO-8859-1
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(""))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String result1 = result.getResponse().getContentAsString(Charset.defaultCharset());
        System.out.println(result1);
        Assert.assertEquals(MessageConstant.PARAMS_ERROR_CODE, JSON.parseObject(result1).get("code"));
        Assert.assertEquals(MessageConstant.PARAMS_TYPE_ERROR, JSON.parseObject(result1).get("message"));

    }

    /**
     * @Author luojiarui
     * @Description 批量拒绝文档的上传
     * 没有参数, 类型转换错误
     * @Date 22:33 2022/12/8
     * @Param []
     * @return void
     **/
    @Test
    public void refuseBatchTest3() throws Exception {
        BatchIdDTO batchIdDTO = new BatchIdDTO();
        List<String> ids = Lists.newArrayList("a", "b", "c");
//        batchIdDTO.setIds(ids);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .post("/docReview/refuseBatch")
                // 设置返回值类型为utf-8，否则默认为ISO-8859-1
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSON.toJSONString(batchIdDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String result1 = result.getResponse().getContentAsString(Charset.defaultCharset());
        System.out.println(result1);
        Assert.assertEquals(MessageConstant.PARAMS_ERROR_CODE, JSON.parseObject(result1).get("code"));
        Assert.assertEquals(MessageConstant.PARAMS_IS_NOT_NULL, JSON.parseObject(result1).get("message"));
    }

    @Test
    public void queryReviewResultList() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/document/list")
                // 设置返回值类型为utf-8，否则默认为ISO-8859-1
                .accept(MediaType.APPLICATION_JSON)
                .param("type", "ALL")
                .param("filterWord", "")
                .param("rows", "10")
                .param("categoryId", "1")
                .param("tagid", "1")
                .param("page", "1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void removeDocReviewTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/document/list")
                // 设置返回值类型为utf-8，否则默认为ISO-8859-1
                .accept(MediaType.APPLICATION_JSON)
                .param("type", "ALL")
                .param("filterWord", "")
                .param("rows", "10")
                .param("categoryId", "1")
                .param("tagid", "1")
                .param("page", "1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void queryLogList() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/document/list")
                // 设置返回值类型为utf-8，否则默认为ISO-8859-1
                .accept(MediaType.APPLICATION_JSON)
                .param("type", "ALL")
                .param("filterWord", "")
                .param("rows", "10")
                .param("categoryId", "1")
                .param("tagid", "1")
                .param("page", "1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    public void removeLogTest1() throws Exception {

        Map<String, List<String>> content = new HashMap<>(8);
        List<String> tmp = Lists.newArrayList("1", "2", "3");
        content.put("ids", tmp);
        JSON json = (JSON) JSON.toJSON(content);


        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .delete("/docReview/removeDocReview")
                .contentType(MediaType.APPLICATION_JSON)
                // 设置返回值类型为utf-8，否则默认为ISO-8859-1
                .accept(MediaType.APPLICATION_JSON)
                .content(json.toJSONString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String result1 = result.getResponse().getContentAsString(Charset.defaultCharset());
        System.out.println(result1);

    }
}