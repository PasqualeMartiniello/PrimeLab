<%@page import="it.unisa.gitdm.bean.Model"%>
<%@page import="it.unisa.primeLab.ProjectHandler"%>
<%@page import="it.unisa.gitdm.bean.Project"%>
<%@page import="java.util.ArrayList"%>
<jsp:include page="header.jsp" />
<% ArrayList<Model> models = (ArrayList<Model>) session.getAttribute("models");%>
<!-- top navigation -->
<!--<div class="top_nav">
    <div class="nav_menu">
        <nav>
            <div class="nav toggle">
                <a id="menu_toggle"><i class="fa fa-bars"></i></a>
            </div>

            <ul class="nav navbar-nav navbar-right">
                <li class="">
                    <label class="breadcrumb"><a href="#"> Pagina 1 </a> | <a href="#"> Pagina 2 </a> | Pagina 3 </label>

                </li>
            </ul>
        </nav>
    </div>
</div>-->
<!-- /top navigation -->
<!-- page content -->
<div class="right_col" role="main">
    <div class="">
        <div class="page-title">
            <div class="title_left">
                <h3>Titolo</h3>
            </div>

            <div class="title_right">

            </div>
        </div>

        <div class="clearfix"></div>


    </div>
    <!-- CONTENT -->
    <div class="row">
        <div class="col-md-12 col-sm-12 col-xs-12">
            <div class="x_panel">
                <div class="x_title">
                    <h2>Default Example <small>Users</small></h2>

                    <div class="clearfix"></div>
                </div>
                <div class="x_content">
                    <table id="datatable" class="table table-striped table-bordered">
                        <thead>
                            <tr>
                                <th>Project Name</th>
                                <th>Within/Cross</th>
                                <th>Projects</th>
                                <th>URL</th>
                                <th>Metrics</th>
                                <th>Classifier</th>
                                <th>Date</th>
                            </tr>
                        </thead>


                        <tbody> 
                            <%
                                for (Model m : models){
                                    String metrics = m.getMetrics().toString();
                                    metrics = metrics.substring(1,metrics.length() -1);
                                    out.print("<tr><a><td>"+m.getProjName()+"</td>");
                                    if(m.isCross()){
                                        out.print("<td>Cross</td>");
                                        out.print("<td>");
                                        for(String p : m.getProjects()) {
                                                 out.print(p+";");
                                        }
                                        out.print("</td>");
                                    }
                                    else {
                                        out.print("<td>Within</td>");
                                        out.print("<td>None</td>");
                                    }
                                    
                                    out.print("<td>"+m.getProjURL()+"</td>");
                                    out.print("<td>"+metrics+"</td>");
                                    out.print("<td>"+m.getClassifier()+"</td>");
                                    out.print("<td>"+m.getDate()+"</td>");
                                }
                            %>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
    <!-- /CONTENT -->
</div>


<!-- /page content -->

<!-- footer content -->
<jsp:include page="footer.jsp" />


<script src="scripts/datatables.net/js/jquery.dataTables.min.js"></script>

<script src="scripts/datatables.net-bs/js/dataTables.bootstrap.min.js"></script>

<script>
    $(document).ready(function () {
        var handleDataTableButtons = function () {
            if ($("#datatable-buttons").length) {
                $("#datatable-buttons").DataTable({
                    dom: "Bfrtip",
                    buttons: [
                        {
                            extend: "copy",
                            className: "btn-sm"
                        },
                        {
                            extend: "csv",
                            className: "btn-sm"
                        },
                        {
                            extend: "excel",
                            className: "btn-sm"
                        },
                        {
                            extend: "pdfHtml5",
                            className: "btn-sm"
                        },
                        {
                            extend: "print",
                            className: "btn-sm"
                        },
                    ],
                    responsive: true
                });
            }
        };

        TableManageButtons = function () {
            "use strict";
            return {
                init: function () {
                    handleDataTableButtons();
                }
            };
        }();

        $('#datatable').dataTable({
            'order': [[1, 'asc']],
            'columnDefs': [
                {orderable: false, targets: [2,3]}
            ],
            'iDisplayLength': 10,
        });

        $('#datatable-keytable').DataTable({
            keys: true
        });

        $('#datatable-responsive').DataTable();

        $('#datatable-scroller').DataTable({
            ajax: "js/datatables/json/scroller-demo.json",
            deferRender: true,
            scrollY: 380,
            scrollCollapse: true,
            scroller: true
        });

        $('#datatable-fixed-header').DataTable({
            fixedHeader: true
        });

        var $datatable = $('#datatable-checkbox');

        $datatable.dataTable({
            'order': [[1, 'asc']],
            'columnDefs': [
                {orderable: false, targets: [0]}
            ]
        });
        
        $datatable.on('draw.dt', function () {
            $('input').iCheck({
                checkboxClass: 'icheckbox_flat-green'
            });
        });

        TableManageButtons.init();
    });
</script>