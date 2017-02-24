<%@page import="it.unisa.primeLab.Config"%>
<%@page import="it.unisa.gitdm.bean.Model"%>
<%@page import="it.unisa.primeLab.ProjectHandler"%>
<%@page import="it.unisa.gitdm.bean.Project"%>
<%@page import="java.util.ArrayList"%>
<jsp:include page="header.jsp" />
<% ArrayList<Project> projects = ProjectHandler.getAllProjects(); %>
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
                <h3>Explore Datasets</h3>
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
                    <h2>Datasets</h2>

                    <div class="clearfix"></div>
                </div>
                <div class="x_content">
                    <table id="datatable" class="table table-striped table-bordered">
                            <thead>
                                <tr>
                                    <th>Project Name</th>
                                    <th>Github URL</th>
                                    <th>N. Old Computation</th>
                                    <th></th>
                                </tr>
                            <tbody>
                                 <%
                                     for(Project p : projects){
                                        out.print("<tr><td style=\"vertical-align:middle\">" + p.getName() + "</td>");
                                        if(p.getGitURL() == null) {
                                            out.print("<td style=\"vertical-align:middle\">No Link Availaeble</td>");
                                        } else {
                                            out.print("<td style=\"vertical-align:middle\">" + p.getGitURL() + "</td>");
                                        }
                                        if(p.getModels() == null) {
                                         out.print("<td style=\"vertical-align:middle\">0</td>");   
                                        } else {
                                            out.print("<td style=\"vertical-align:middle\">" + p.getModels().size() + "</td>");
                                        }
                                        out.print("<td style=\"text-align:center\"><button class=\"btn btn-default\"><a href=\"file://"+Config.baseDir + p.getName() +"/predictors.csv\" class=\"fa fa-download\" download></button></td></tr>");
                                     }
                                     %>
                            </tbody>
                            </thead>
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