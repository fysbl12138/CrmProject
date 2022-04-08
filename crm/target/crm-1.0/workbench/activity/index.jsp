<%
    String basePath = request.getScheme() + "://" +
            request.getServerName() + ":" +
            request.getServerPort() +
            request.getContextPath() + "/";
%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <base href="<%= basePath%>">
    <meta charset="UTF-8">

    <link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet"/>
    <link href="jquery/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css" type="text/css"
          rel="stylesheet"/>

    <script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
    <script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>

    <script type="text/javascript"
            src="jquery/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.js"></script>
    <script type="text/javascript"
            src="jquery/bootstrap-datetimepicker-master/locale/bootstrap-datetimepicker.zh-CN.js"></script>

    <link rel="stylesheet" type="text/css" href="jquery/bs_pagination/jquery.bs_pagination.min.css">
    <script type="text/javascript" src="jquery/bs_pagination/jquery.bs_pagination.min.js"></script>
    <script type="text/javascript" src="jquery/bs_pagination/en.js"></script>


    <script type="text/javascript">

        $(function () {

            $("#addBtn").click(function () {

                //时间插件
                $(".time").datetimepicker({
                    minView : "month",
                    language : "zh-CN",
                    format : "yyyy-mm-dd",
                    autoclose : true,
                    todayBtn : true,
                    pickerPosition : "bottom-left"
                })

                //填充人物列表
                $.ajax({
                    url : "workbench/activity/getUserList.do",
                    type : "get",
                    dataType : "json",
                    success : function (data) {

                        var html = "<option></option>";

                        $.each(data,function (i,n) {
                            html += "<option value='"+n.id+"'>"+n.name+"</option>"
                        })

                        $("#create-marketActivityOwner").html(html);

                        $("#create-marketActivityOwner").val("${user.id}");

                        $("#createActivityModal").modal("show");
                    }
                })
            })

            //点击保存后的事件
            $("#saveBtn").click(function () {
                $.ajax({
                    url : "workbench/activity/save.do",
                    data : {
                        "owner" : $.trim($("#create-marketActivityOwner").val()),
                        "name" : $.trim($("#create-marketActivityName").val()),
                        "startDate" : $.trim($("#create-startTime").val()),
                        "endDate" : $.trim($("#create-endTime").val()),
                        "cost" : $.trim($("#create-cost").val()),
                        "description" : $.trim($("#create-describe").val())
                    },
                    type : "post",
                    dataType : "json",
                    success : function (data) {

                        if (data){

                            //清空表单
                            $("#formAdd")[0].reset();

                            //关闭模态窗口
                            $("#createActivityModal").modal("hide");

                            pageList(1,$("#pageList").bs_pagination('getOption', 'rowsPerPage'));


                        }else {
                            alert("添加市场活动错误")
                        }
                    }
                })
            })

            pageList(1,2);

            $("#SearchsubmitBtn").click(function () {

                $("#hidden-name").val($.trim($("#search-name").val()));
                $("#hidden-owner").val($.trim($("#search-owner").val()));
                $("#hidden-startDate").val($.trim($("#search-startDate").val()));
                $("#hidden-endDate").val($.trim($("#search-endDate").val()));


                pageList(1,2);

            })

            $("#qx").click(function () {
              $("input[name=xz]").prop("checked",this.checked)
            })

            $("#tbaodyPageList").on("click",$("input[name=xz]"),function () {
                $("#qx").prop("checked",$("input[name=xz]").length==$("input[name=xz]:checked").length)
            });

            $("#deleteBtn").click(function () {

                var $xz = $("input[name=xz]:checked");

                if($xz.length == 0){

                    alert("请选择需要删除的记录")

                }else{
                    if(confirm("您确定要删除所选记录吗？？")){

                        var param= "";
                        for (var i = 0;i<$xz.length;i++){

                            param += "id="+$($xz[i]).val();

                            if(i<$xz.length-1){
                                param += "&";
                            }

                        }
                        // alert(param)

                        $.ajax({
                            url : "workbench/activity/delete.do",
                            data : param,
                            type : "post",
                            dataType : "json",
                            success : function (data) {

                                if (data.success){

                                    pageList(1,$("#pageList").bs_pagination('getOption', 'rowsPerPage'));
                                }else {
                                    alert("删除信息失败")
                                }
                            }
                        })

                    }
                }
            })

            $("#updataBtn").click(function () {
                // alert("你好");
                var $xz = $("input[name=xz]:checked");

                if($xz.length == 0){
                    alert("请选择需要修改的记录");
                }else if ($xz.length > 1){
                    alert("请选择一条记录进行修改")
                }else {

                    var id = $xz.val();
                    $.ajax({
                        url : "workbench/activity/updata.do",
                        data : {
                            "id" : id
                        },
                        type : "post",
                        dataType : "json",
                        success : function (data) {
                            var html = "<option></option>"
                            
                            $.each(data.uList,function (i,n) {
                                html += "<option value='"+n.id+"'>"+n.name+"</option>"

                            })
                            $("#edit-marketActivityOwner").html(html)
                            $("#edit-marketActivityOwner").val("${user.id}");

                            $("#edit-id").val(data.a.id);
                            $("#edit-marketActivityName").val(data.a.name);
                            $("#edit-startTime").val(data.a.startDate);
                            $("#edit-endTime").val(data.a.endDate);
                            $("#edit-cost").val(data.a.cost);
                            $("#edit-describe").val(data.a.description);

                            $("#editActivityModal").modal("show");
                        }
                    })
                }
            })

            $("#updataSubmitBtn").click(function () {
                $.ajax({
                    url : "workbench/activity/updataSubmit.do",
                    data : {
                        "id" : $.trim($("#edit-id").val()),
                        "owner" : $.trim($("#edit-marketActivityOwner").val()),
                        "name" : $.trim($("#edit-marketActivityName").val()),
                        "startDate" : $.trim($("#edit-startTime").val()),
                        "endDate" : $.trim($("#edit-endTime").val()),
                        "cost" : $.trim($("#edit-cost").val()),
                        "description" : $.trim($("#edit-describe").val())
                    },
                    type : "post",
                    dataTyp  : "json",
                    success : function (data) {

                        pageList($("#pageList").bs_pagination('getOption', 'currentPage')
                            ,$("#pageList").bs_pagination('getOption', 'rowsPerPage'));

                        $("#editActivityModal").modal("hide");

                    }
                })
            })
        });

        function pageList(pageNo,pageSize) {

            /**
             * pageList($("#activityPage").bs_pagination('getOption', 'currentPage')
             ,$("#activityPage").bs_pagination('getOption', 'rowsPerPage'));

             *
             *
             */


            $("#qx").prop("checked",false);

            $("#search-name").val($.trim($("#hidden-name").val()));
            $("#search-owner").val($.trim($("#hidden-owner").val()));
            $("#search-startDate").val($.trim($("#hidden-startDate").val()));
            $("#search-endDate").val($.trim($("#hidden-endDate").val()));


            $.ajax({
                url: "workbench/activity/pageList.do",
                data: {
                    "pageNo" : pageNo,
                    "pageSize" : pageSize,
                    "owner": $.trim($("#search-owner").val()),
                    "name": $.trim($("#search-name").val()),
                    "startDate": $.trim($("#search-startDate").val()),
                    "endDate": $.trim($("#search-endDate").val()),
                },
                type: "get",
                dataType: "json",
                success: function (data) {

                    var html = "";

                    $.each(data.dataList,function (i,n) {

                        html += '<tr class="active">';
                        html += '<td><input type="checkbox" name="xz" value="'+n.id+'"/></td>';
                        html += '<td><a style="text-decoration: none;cursor: pointer;" onclick="window.location.href=\'workbench/activity/detail.do?id='+n.id+'\';">'+n.name+'</a></td>';
                        html += '<td>'+n.owner+'</td>';
                        html += '<td>'+n.startDate+'</td>';
                        html += '<td>'+n.endDate+'</td>';
                        html += '</tr>';

                    })

                    $("#tbaodyPageList").html(html);

                    var totalPages = data.total%pageSize==0?data.total/pageSize:parseInt(data.total/pageSize)+1;


                    $("#pageList").bs_pagination({
                        currentPage: pageNo, // 页码
                        rowsPerPage: pageSize, // 每页显示的记录条数
                        maxRowsPerPage: 20, // 每页最多显示的记录条数
                        totalPages: totalPages, // 总页数
                        totalRows: data.total, // 总记录条数

                        visiblePageLinks: 3, // 显示几个卡片

                        showGoToPage: true,
                        showRowsPerPage: true,
                        showRowsInfo: true,
                        showRowsDefaultInfo: true,

                        onChangePage : function(event, data){
                            pageList(data.currentPage , data.rowsPerPage);
                        }
                    });
                }
            });
        }
    </script>
</head>
<body>

<input type="hidden" id="hidden-name"/>
<input type="hidden" id="hidden-owner"/>
<input type="hidden" id="hidden-startDate"/>
<input type="hidden" id="hidden-endDate"/>

<!-- 创建市场活动的模态窗口 -->
<div class="modal fade" id="createActivityModal" role="dialog">
    <div class="modal-dialog" role="document" style="width: 85%;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">
                    <span aria-hidden="true">×</span>
                </button>
                <h4 class="modal-title" id="myModalLabel1">创建市场活动</h4>
            </div>
            <div class="modal-body">

                <form class="form-horizontal" role="form" id="formAdd">

                    <div class="form-group">
                        <label for="create-marketActivityOwner" class="col-sm-2 control-label">所有者<span
                                style="font-size: 15px; color: red;">*</span></label>
                        <div class="col-sm-10" style="width: 300px;">
                            <select class="form-control" id="create-marketActivityOwner">

                            </select>
                        </div>
                        <label for="create-marketActivityName" class="col-sm-2 control-label">名称<span
                                style="font-size: 15px; color: red;">*</span></label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control" id="create-marketActivityName">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="create-startTime" class="col-sm-2 control-label">开始日期</label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control time" id="create-startTime" readonly>
                        </div>
                        <label for="create-endTime" class="col-sm-2 control-label">结束日期</label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control time" id="create-endTime" readonly>
                        </div>
                    </div>
                    <div class="form-group">

                        <label for="create-cost" class="col-sm-2 control-label">成本</label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control" id="create-cost">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="create-describe" class="col-sm-2 control-label">描述</label>
                        <div class="col-sm-10" style="width: 81%;">
                            <textarea class="form-control" rows="3" id="create-describe"></textarea>
                        </div>
                    </div>

                </form>

            </div>
            <div class="modal-footer">


                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" id="saveBtn">保存</button>
            </div>
        </div>
    </div>
</div>

<!-- 修改市场活动的模态窗口 -->
<div class="modal fade" id="editActivityModal" role="dialog">
    <div class="modal-dialog" role="document" style="width: 85%;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">
                    <span aria-hidden="true">×</span>
                </button>
                <h4 class="modal-title" id="myModalLabel2">修改市场活动</h4>
            </div>
            <div class="modal-body">

                <form class="form-horizontal" role="form">
                    <input type="hidden" id="edit-id">

                    <div class="form-group">
                        <label for="edit-marketActivityOwner" class="col-sm-2 control-label">所有者<span
                                style="font-size: 15px; color: red;">*</span></label>
                        <div class="col-sm-10" style="width: 300px;">
                            <select class="form-control" id="edit-marketActivityOwner">

                            </select>
                        </div>
                        <label for="edit-marketActivityName" class="col-sm-2 control-label">名称<span
                                style="font-size: 15px; color: red;">*</span></label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control" id="edit-marketActivityName">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="edit-startTime" class="col-sm-2 control-label time">开始日期</label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control" id="edit-startTime" >
                        </div>
                        <label for="edit-endTime" class="col-sm-2 control-label time">结束日期</label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control" id="edit-endTime" >
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="edit-cost" class="col-sm-2 control-label">成本</label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control" id="edit-cost" >
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="edit-describe" class="col-sm-2 control-label">描述</label>
                        <div class="col-sm-10" style="width: 81%;">
                            <textarea class="form-control" rows="3" id="edit-describe"></textarea>
                        </div>
                    </div>

                </form>

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" id="updataSubmitBtn">更新</button>
            </div>
        </div>
    </div>
</div>


<div>
    <div style="position: relative; left: 10px; top: -10px;">
        <div class="page-header">
            <h3>市场活动列表</h3>
        </div>
    </div>
</div>
<div style="position: relative; top: -20px; left: 0px; width: 100%; height: 100%;">
    <div style="width: 100%; position: absolute;top: 5px; left: 10px;">

        <div class="btn-toolbar" role="toolbar" style="height: 80px;">
            <form class="form-inline" role="form" style="position: relative;top: 8%; left: 5px;">

                <div class="form-group">
                    <div class="input-group">
                        <div class="input-group-addon">名称</div>
                        <input class="form-control" type="text" id="search-name">
                    </div>
                </div>

                <div class="form-group">
                    <div class="input-group">
                        <div class="input-group-addon">所有者</div>
                        <input class="form-control" type="text" id="search-owner">
                    </div>
                </div>


                <div class="form-group">
                    <div class="input-group">
                        <div class="input-group-addon">开始日期</div>
                        <input type="text" class="form-control time" id="search-startTime"/>
                    </div>
                </div>
                <div class="form-group">
                    <div class="input-group">
                        <div class="input-group-addon">结束日期</div>
                        <input type="text" class="form-control time" id="search-endTime">
                    </div>
                </div>

                <button type="button" id="SearchsubmitBtn" class="btn btn-default">查询</button>

            </form>
        </div>
        <div class="btn-toolbar" role="toolbar"
             style="background-color: #F7F7F7; height: 50px; position: relative;top: 5px;">
            <div class="btn-group" style="position: relative; top: 18%;">
                <button type="button" class="btn btn-primary" id="addBtn">
                    <span class="glyphicon glyphicon-plus"></span> 创建
                </button>
                <button type="button" class="btn btn-default" id="updataBtn"><span
                        class="glyphicon glyphicon-pencil"></span> 修改
                </button>
                <button type="button" class="btn btn-danger" id="deleteBtn"><span class="glyphicon glyphicon-minus"></span> 删除</button>
            </div>

        </div>
        <div style="position: relative;top: 10px;">
            <table class="table table-hover">
                <thead>
                <tr style="color: #B3B3B3;">
                    <td><input type="checkbox" id="qx"/></td>
                    <td>名称</td>
                    <td>所有者</td>
                    <td>开始日期</td>
                    <td>结束日期</td>
                </tr>
                </thead>
                <tbody id="tbaodyPageList">

                </tbody>
            </table>
        </div>

        <div style="height: 50px; position: relative;top: 30px;">

            <div id="pageList"></div>

        </div>

    </div>

</div>
</body>
</html>