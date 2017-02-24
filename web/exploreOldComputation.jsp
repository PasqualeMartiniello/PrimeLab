<%-- 
    Document   : exploreOldComputation
    Created on : 16-feb-2017, 8.49.22
    Author     : pasqualemartiniello
--%>
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
                <h3>Explore Old Computation</h3>
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
                    <h2>Computations</h2>

                    <div class="clearfix"></div>
                </div>
                <div class="x_content">
                    <form action="CompareServlet" method="GET" id="demo-form2">
                        <table id="datatable2" class="table table-striped table-bordered">
                            <thead>
                                <tr>
                                    <th></th>
                                    <th>Project Name</th>
                                    <th>Within/Cross</th>
                                    <th>Projects</th>
                                    <th>URL</th>
                                    <th>Metrics</th>
                                    <th>Classifier</th>
                                    <th>Date</th>
                                    <th></th>
                                </tr>
                            </thead>


                            <tbody> 
                                <%
                                    for (Model m : models) {
                                        String metrics = m.getMetrics().toString();
                                        metrics = metrics.substring(1, metrics.length() - 1);
                                        out.print("<tr><td style=\"vertical-align:middle\"><input type=\"checkbox\" class=\"flat\""
                                                + "value=\"" + m.getProjName() + "-" + m.isCross() + "-" + m.getProjects() + "-" + m.getProjURL() + "-" + m.getMetrics() + "-" + m.getClassifier() + "\" "
                                                + "name=\"table_records\"></td>");
                                        out.print("<a><td style=\"vertical-align:middle\">" + m.getProjName() + "</td>");
                                        if (m.isCross()) {
                                            out.print("<td style=\"vertical-align:middle\">Cross</td>");
                                            out.print("<td style=\"vertical-align:middle\">");
                                            for (String p : m.getProjects()) {
                                                out.print(p + ";");
                                            }
                                            out.print("</td>");
                                        } else {
                                            out.print("<td style=\"vertical-align:middle\">Within</td>");
                                            out.print("<td style=\"vertical-align:middle\">None</td>");
                                        }

                                        out.print("<td style=\"vertical-align:middle\">" + m.getProjURL() + "</td>");
                                        out.print("<td style=\"vertical-align:middle\">" + metrics + "</td>");
                                        out.print("<td style=\"vertical-align:middle\">" + m.getClassifier() + "</td>");
                                        out.print("<td style=\"vertical-align:middle\">" + m.getDate() + "</td>");
                                        out.print("<td><button ");
                                        out.print("value=\"" + m.getProjName() + "-" + m.isCross() + "-" + m.getProjects() + "-" + m.getProjURL() + "-" + m.getMetrics() + "-" + m.getClassifier() + "\" ");
                                        out.print("class=\"btn btn-default\" onclick=\"modelClicked(this.value)\">");
                                        out.print("<a class=\"fa fa-bar-chart\"></button></td>");
                                    }
                                %>
                            </tbody>
                        </table>
                        <button type="submit" class="btn btn-primary">Compare</button>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <div id="prediction">


    </div>
    <!-- /CONTENT -->
</div>


<!-- /page content -->

<!-- footer content -->
<jsp:include page="footer.jsp" />

<script>
    $(document).ready(function () {
        $('#datatable2').dataTable({
            'order': [[1, 'asc']],
            'columnDefs': [
            {orderable: false, targets: [0, 2, 3, 8]}
            ],
        });
        $('input').iCheck();
    });
    
    function modelClicked(value){
        var split = value.split("-");
        $.blockUI({ message: '' });
        $.ajax({
            url: 'LoadComputation',
            type: 'GET',
            data: {
                'projName': split[0],
                'isCross': split[1],
                'projects': split[2],
                'github': split[3],
                'metrics': split[4],
                'classifier': split[5],
            },
            dataType: 'json',
            success: function(result) {
                loadDivPrediction(result);
                $.unblockUI();
            }
        });
    }


    function loadDivPrediction(data) {
        $('#prediction').load('newPrediction.jsp', function() {
        Morris.Bar({
            element: 'graph',
                data: [
                {x: 'Accuracy', y: data[0].accuracy},
                {x: 'Precision', y: data[0].precision},
                {x: 'Recall', y: data[0].recall},
                {x: 'Fmeasure', y: data[0].fmeasure},
                {x: 'AreaUnderROC', y: data[0].areaUnderRoc}
                ],
                xkey: 'x',
                ykeys: ['y'],
                barColors: ['#26B99A', '#34495E', '#ACADAC', '#3498DB'],
                hideHover: 'auto',
                labels: [data[1].classifier],
                resize: true
                }).on('click', function (i, row) {
                console.log(i, row);
            });
            var classes = data[2];
            for(i = 0; i < classes.length; i++){
                $('#tableBody').append("<tr><td>" + classes[i].classPath + "</td><td>" + classes[i].LOC + "</td><td>" + classes[i].CBO + "</td><td>" 
                        + classes[i].LCOM + "</td><td>" + classes[i].NOM + "</td><td>" +classes[i].RFC + "</td><td>" + classes[i].WMC + "</td><td>" 
                        + classes[i].numOfChanges + "</td><td>" + classes[i].numberOfFIChanges + "</td><td>" + classes[i].structuralScatteringSum + "</td><td>"
                        + classes[i].semanticScatteringSum + "</td><td>" + classes[i].numberOfDeveloper + "</td><td>" + classes[i].isBuggy + "</td></tr>");
            }
            $('#datatable').dataTable({
            'order': [[0, 'asc']],
            });
            $('html,body').animate({ scrollTop: $(document).height() - $(window).height() }, 'slow');
        });
    }

    $('#demo-form2').submit(function(){
        $.blockUI({ message: '' });
        $.ajax({
            url: $(this).attr('action'),
            type: $(this).attr('method'),
            data: $(this).serialize(),
            dataType: 'json',
            success: function(result) {
                loadMorePrediction(result);
                $.unblockUI();
                $('input[name="table_records"]').each(function(){
                   $(this).iCheck('uncheck'); 
                });
            }
        });
        return false;
    });
    
    function loadMorePrediction(data) {
        if(data.length === 1) {
            alert("Select more computations");  
        } else if (data.length === 2){
            $('#prediction').load('newPrediction.jsp', function() {
            Morris.Bar({
                element: 'graph',
                    data: [
                    {x: 'Accuracy', y: data[0].accuracy,z: data[1].accuracy},
                    {x: 'Precision', y: data[0].precision, z: data[1].precision},
                    {x: 'Recall', y: data[0].recall, z: data[1].recall },
                    {x: 'Fmeasure', y: data[0].fmeasure, z: data[1].fmeasure},
                    {x: 'AreaUnderROC', y: data[0].areaUnderRoc, z: data[1].areaUnderRoc}
                    ],
                    xkey: 'x',
                    ykeys: ['y', 'z'],
                    barColors: ['#26B99A', '#ACADAC', '#3498DB'],
                    hideHover: 'auto',
                    labels: ['Fisrt Model', 'Second Model'],
                    resize: true
                }).on('click', function (i, row) {
                    console.log(i, row);
                });
            $('#section2').hide();
            $('html,body').animate({ scrollTop: $(document).height() - $(window).height() }, 'slow');
            });
        } else if(data.length === 3){
            $('#prediction').load('newPrediction.jsp', function() {
            Morris.Bar({
                element: 'graph',
                    data: [
                    {x: 'Accuracy', y: data[0].accuracy,z: data[1].accuracy,a: data[2].accuracy},
                    {x: 'Precision', y: data[0].precision, z: data[1].precision, a: data[2].precision},
                    {x: 'Recall', y: data[0].recall, z: data[1].recall, a: data[2].recall},
                    {x: 'Fmeasure', y: data[0].fmeasure, z: data[1].fmeasure, a: data[2].fmeasure},
                    {x: 'AreaUnderROC', y: data[0].areaUnderRoc, z: data[1].areaUnderRoc, a: data[2].areaUnderRoc}
                    ],
                    xkey: 'x',
                    ykeys: ['y', 'z', 'a'],
                    barColors: ['#26B99A', '#ACADAC', '#3498DB'],
                    hideHover: 'auto',
                    labels: ['Fisrt Model', 'Second Model', 'Third Model'],
                    resize: true
                }).on('click', function (i, row) {
                    console.log(i, row);
                });
            $('#section2').hide();
            $('html,body').animate({ scrollTop: $(document).height() - $(window).height() }, 'slow');
            });
        }  else {
            $('#prediction').load('newPrediction.jsp', function() {
            Morris.Bar({
                element: 'graph',
                    data: [
                    {x: 'Accuracy', y: data[0].accuracy,z: data[1].accuracy,a: data[2].accuracy, b: data[3].accuracy},
                    {x: 'Precision', y: data[0].precision, z: data[1].precision, a: data[2].precision, b: data[3].precision},
                    {x: 'Recall', y: data[0].recall, z: data[1].recall, a: data[2].recall, b: data[3].recall},
                    {x: 'Fmeasure', y: data[0].fmeasure, z: data[1].fmeasure, a: data[2].fmeasure, b: data[3].fmeasure},
                    {x: 'AreaUnderROC', y: data[0].areaUnderRoc, z: data[1].areaUnderRoc, a: data[2].areaUnderRoc, b: data[3].areaUnderRoc}
                    ],
                    xkey: 'x',
                    ykeys: ['y', 'z', 'a', 'b'],
                    barColors: ['#26B99A', '#ACADAC', '#3498DB'],
                    hideHover: 'auto',
                    labels: ['Fisrt Model', 'Second Model', 'Third Model', 'Fourth Model'],
                    resize: true
                }).on('click', function (i, row) {
                    console.log(i, row);
                });
            $('#section2').hide();
            $('html,body').animate({ scrollTop: $(document).height() - $(window).height() }, 'slow');
            });
        } 
    }
</script>