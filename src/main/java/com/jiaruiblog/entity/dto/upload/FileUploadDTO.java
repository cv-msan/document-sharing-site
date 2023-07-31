package com.jiaruiblog.entity.dto.upload;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @ClassName FileUploadDTO
 * @Description 文件批量上传的参数
 * @Author luojiarui
 * @Date 2023/4/21 23:12
 * @Version 1.0
 **/
@Data
@ApiModel(value = "用户进行批量上传的", description = "各类分页数据列表查询的实体")
public class FileUploadDTO {

    @ApiModelProperty(value = "文件", notes = "批量文件上传信息")
    private MultipartFile[] files;

    @ApiModelProperty(value = "分类", notes = "文档的分类信息，如果有则分类的长度限制为64字符，不能有空格和换行符号")
    private String category;

    @ApiModelProperty(value = "标签", notes = "文档的标签信息，如果有则标签的长度限制为64字符，不能有空格和换行符号")
    private List<String> tags;

    @ApiModelProperty(value = "描述",notes = "文档的描述信息")
    private String description;

    @ApiModelProperty(value = "跳过错误", notes = "如果开启为True，则文件上传过程中出错是直接往后进行")
    private Boolean skipError;
    @ApiModelProperty(value = "标题", notes = "批量文件上传信息")
    private String title;

    @ApiModelProperty(value = "关键字", notes = "文档的标签信息，如果有则标签的长度限制为64字符，不能有空格和换行符号")
    private List<String> keywords;

    @ApiModelProperty(value = "公开范围",notes = "文档的描述信息")
    private String public_scope;

    @ApiModelProperty(value = "内容摘要", notes = "如果开启为True，则文件上传过程中出错是直接往后进行")
    private String content_summary;
    @ApiModelProperty(value = "备注",notes = "文档的描述信息")
    private String remarks;

    @ApiModelProperty(value = "预览图", notes = "上传文件的预览图，允许为空")
    private String preview_image;
    @ApiModelProperty(value = "点赞量",notes = "文件的点赞数量")
    private Integer likes;
    @ApiModelProperty(value = "收藏量", notes = "文件的收藏数量")
    private Integer favorites;
    @ApiModelProperty(value = "下载量", notes = "文件的下载数量")
    private Integer downloads;
    @ApiModelProperty(value = "浏览量", notes = "文件的浏览数量")
    private Integer views;
    @ApiModelProperty(value = "文件扩展名", notes = "附件的扩展名")
    private String file_extension;
    @ApiModelProperty(value = "是否允许下载", notes = "附件是否允许下载，0为是，1为否")
    private Integer allow_download;
    @ApiModelProperty(value = "审核状态", notes = "文档的审核状态，-1为审核不通过，0为待审核，1为审核通过")
    private Integer approval_status;
    @ApiModelProperty(value = "评论次数", notes = "文档的评论次数")
    private Integer comments;
    @ApiModelProperty(value = "公开范围详情", notes = "公开范围详情，如果公开范围为部门级公开那么就存部门的id，如果是自定义公开那么就存人的id")
    private Integer visibility_details;

}

