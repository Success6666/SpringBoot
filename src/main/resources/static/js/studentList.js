$(function () {
	
        //提示信息  初始化设置 一般不需要改
        var lang = {
            "sProcessing": "处理中...",
            "sLengthMenu": "每页 _MENU_ 项",
            "sZeroRecords": "没有匹配结果",
            "sInfo": "当前显示第 _START_ 至 _END_ 项，共 _TOTAL_ 项。",
            "sInfoEmpty": "当前显示第 0 至 0 项，共 0 项",
            "sInfoFiltered": "(由 _MAX_ 项结果过滤)",
            "sInfoPostFix": "",
            "sSearch": "搜索:",
            "sUrl": "",
            "sEmptyTable": "表中数据为空",
            "sLoadingRecords": "载入中...",
            "sInfoThousands": ",",
            "oPaginate": {
                "sFirst": "首页",
                "sPrevious": "上页",
                "sNext": "下页",
                "sLast": "末页",
                "sJump": "跳转"
            },
            "oAria": {
                "sSortAscending": ": 以升序排列此列",
                "sSortDescending": ": 以降序排列此列"
            }
        };


        //重要修改 表格内容的自定义，需要根据业务定制
        var studentTable = $("#studentList").dataTable({
            language:lang,  //提示信息
            aLengthMenu: [2, 5, 10],//设置分页大小下拉选项
            autoWidth: false,  //禁用自动调整列宽
            stripeClasses: ["odd", "even"],  //为奇偶行加上样式，兼容不支持CSS伪类的场合
            processing: true,  //隐藏加载提示,自行处理
            serverSide: true,  //启用服务器端分页
            //paging:false,
            searching: false,  //禁用原生搜索
            ordering:  true,  //禁用排序
            orderMulti: false,  //启用多列排序
            order: [],  //取消默认排序查询,否则复选框一列会出现小箭头
            renderer: "bootstrap",  //渲染样式：Bootstrap和jquery-ui
            pagingType: "simple_numbers",  //分页样式：simple,simple_numbers,full,full_numbers
           /* columnDefs: [{
                "targets": 'nosort',  //列的样式名
                "orderable": false    //包含上样式名‘nosort’的禁止排序
            }],*/
            ajax: function (data, callback, settings) {

                //封装请求参数
                var param = {};
                param.draw=data.draw;
                param.pageSize= data.length;//页面显示记录条数，在页面显示每页显示多少项的时候
                param.startIndex = data.start;//开始的记录序号
                param.pageIndex = (data.start / data.length);//当前页码
                param.studentSex= $("#txtSex").val().trim();	
                param.studentAge= $("#txtAge").val().trim();
                if(data.order[0]!=null){
                	var orderColumnIndex=data.order[0].column;//获取排序列的序号
                	param.orderColumn=data.columns[orderColumnIndex].data;//获取排序列的名称（不是列头名）
                    param.orderDirection=data.order[0].dir;//获取排序方向
                }              
                //ajax请求数据方法
                $.ajax({
                    type: "GET",
                    url: "/admin/getStudentList",//url请求的地址
                    cache: false,  //禁用缓存
                    data: param,  //传入组装的参数
                    dataType: "json",
                    success: function (result) {
                            //封装返回数据重要
                            var returnData = {};
                            //这里直接自行返回了draw计数器,应该由后台返回
                            returnData.draw = result.draw;
                            //返回数据全部记录
                            returnData.recordsTotal = result.recordsTotal;
                            //后台不实现过滤功能，每次查询均视作全部结果
                            returnData.recordsFiltered = result.recordsFiltered;
                            //返回的数据列表
                            returnData.data = result.data;
                            //调用DataTables提供的callback方法，代表数据已封装完成并传回DataTables进行渲染
                            //此时的数据需确保正确无误，异常判断应在执行此回调前自行处理完毕
                            callback(returnData);
                    }
                });
            },
            //列表表头字段
            columns: [
                { "data": "stdnum" , orderable :true },
                { "data": "name", orderable :false },
                { "data": "sex", orderable :false },               
                { "data": "age"}, 
                { "data": "address", orderable :false },
                //新建列的 定义
                {
                    className : "td-operation text-center",
                    data: null,
                    defaultContent:"",
                    orderable : false,
                    width : "160px"
                }
            ],
            //新建列的 数据内容
            "createdRow": function ( row, data, index ) {
                //行渲染回调,在这里可以对该行dom元素进行任何操作
                var $btn = $('<div class="btn-group text-cen">'+
                        '<button type="button" class="btn btn-sm btn-primary btn-edit" data-toggle="modal" data-target="#modal-lg">修改</button> &nbsp;'+
                        '<button type="button" class="btn btn-sm btn-danger btn-del">删除</button>'+
                        '</div>');
                $('td', row).eq(5).append($btn);
            }
        }).api();
        //此处需调用api()方法,否则返回的是JQuery对象而不是DataTables的API对象
        
        $("#btnShowSearchArea").click(function(){            
            $("#searchCard").slideToggle(1100); 
          });
        
       
        $("#btnSearch").click(function(){
        	studentTable.ajax.reload(null,true); // 刷新表格数据，分页信息不会重置                         
        	studentTable.draw(false);
        });	
        		
        $("#btnClearSearch").click(function(){
            $("#txtSex").val("");	
            $("#txtAge").val("");
        }); 
    }
);


