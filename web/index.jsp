<%@page import="it.unisa.gitdm.bean.Project"%>
<%@page import="it.unisa.primeLab.ProjectHandler"%>
<%@page import="java.util.ArrayList"%>

<% ArrayList<Project> projects = ProjectHandler.getAllProjects(); %>

<jsp:include page="header.jsp" />
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
                <h3>Compute Prediction</h3>
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
                    <h2>Calculate new prediction </h2>

                    <div class="clearfix"></div>
                </div>
                <div class="x_content" style="display: block;">
                    <br>
                    <form action="BuildModelServlet" method="GET" id="demo-form2" data-parsley-validate="" class="form-horizontal form-label-left" novalidate="">

                        <div class="form-group">
                            <label class="control-label col-md-3 col-sm-3 col-xs-12" for="first-name">Project <span class="required">*</span>
                            </label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <div class="col-md-12 col-sm-6 col-xs-12">
                                    <select id="github" onchange="changeProject()" name="github" class="form-control" required="">
                                    <option value="">Choose...</option>
                                     <% for (Project c : projects) {
                                        out.print("<option value=\""+ c.getGitURL() + "\">" + c.getName());
                                        out.print("</option>");
                                    }
                                %> 
                                </select>
                            </div>
                            </div>
                        </div>
                        <div class="ln_solid"></div>
                        <div class="form-group">
                            <label class="control-label col-md-3 col-sm-3 col-xs-12" for="last-name">Select metrics: <span class="required">*</span>
                            </label>
                            <div  class="col-md-6 col-sm-6 col-xs-12">
                                <div class="col-md-4">
                                    <div class="checkbox">
                                        <label class="">
                                            <input type="checkbox" name="metrics" value="CK Metrics" required="required" class="flat" checked="checked"> CK metrics
                                        </label>
                                    </div>
                                </div>
                                <div class="col-md-4">
                                    <div class="checkbox">
                                        <label class="">
                                            <input type="checkbox" name="metrics" value="Process" required="required" class="flat" checked="checked"> Process
                                        </label>
                                    </div>
                                </div>
                                <div class="col-md-4">
                                    <div class="checkbox">
                                        <label class="">
                                            <input type="checkbox" name="metrics" value="Scattering" required="required" class="flat" checked="checked"> Scattering
                                        </label>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="ln_solid"></div>
                        <div class="form-group">
                            <label class="control-label col-md-3 col-sm-3 col-xs-12">Within/Cross Project <span class="required">*</span></label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <div class="col-md-3"></div>
                                <div class="col-md-3">
                                    <input type="radio" class="flat" name="typeProject" required="required" value ="Within Project">
                                    Within Project
                                </div>
                                <div class="col-md-3">
                                    <input type="radio" class="flat" name="typeProject" value="Cross Project" >
                                    Cross Project
                                </div>
                            </div>
                        </div>
                        <div class="ln_solid"></div>
                        <div class="form-group">
                            <label class="control-label col-md-3 col-sm-3 col-xs-12" id="crossLabel">Projects Selection(Cross-Project Prediction) <span class ="required">*</span></label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <% for (Project c : projects) {
                                        out.print("<div class=\"col-md-4\">");
                                        out.print("<div class=\"checkbox\">");
                                        out.print("<label>");
                                        out.print("<input type=\"checkbox\" name=\"projects\"");
                                        out.print("disabled='disabled'");
                                        out.print("class=\"flat\" value=\"" + c.getName() + "\"");
                                        out.print("checked='checked'");
                                        out.print("> " + c.getName());
                                        out.print("</label>");
                                        out.print("</div>");
                                        out.print("</div>");
                                    }
                                %> 
                            </div>
                        </div>
                        <div class="ln_solid"></div>
                        <div class="form-group">
                            <label class="control-label col-md-3 col-sm-3 col-xs-12">Classifier *</label>
                            <div class="col-md-3 col-sm-6 col-xs-12">
                                <select id="classifier" name="classifier" class="form-control" required="">
                                    <option value="">Choose...</option>
                                    <option value="Decision Table Majority">Decision Table Majority</option>
                                    <!--<option value="Logistic Regression">Logistic Regression</option>-->ß
                                    <option value="Multi Layer Perceptron">Multi Layer Perceptron</option>
                                    <option value="Naive Baesian">Naive Baesian</option>
                                    <option value="Random Forest">Random Forest</option>
                                    <option value="Decision Tree">Decision Tree</option>
                                </select>
                            </div>
                            <div class="col-md-3 col-sm-6 col-xs-12 col-md-offset-3">
                                <button type="reset" class="btn btn-danger">Cancel</button>
                                <button type="button" name="confirm" class="btn btn-success">Submit</button>
                                <div id="myModal" class="modal fade bs-example-modal-sm" tabindex="-1" role="dialog" aria-hidden="true">
                                    <div class="modal-dialog modal-sm">
                                        <div class="modal-content">

                                            <div class="modal-header">
                                                <button type="button" onclick="onModalClose()" class="close" data-dismiss="modal"><span aria-hidden="true">×</span>
                                                </button>
                                                <h4 class="modal-title" id="myModalLabel">Are you sure?</h4>
                                            </div>
                                            <div class="modal-body">

<!--                                                <div class="row">
                                                    <label class="control-label col-md-3 col-sm-3 col-xs-12">Github link: </label>
                                                    <label class="control-label col-md-6 col-sm-6 col-xs-12" id="githubConf">link</label>
                                                </div>

                                                <div class="ln_solid"></div>
                                                <div class="row">
                                                    <label class="control-label col-md-3 col-sm-3 col-xs-12">Metrics:</label>
                                                    <label class="control-label col-md-6 col-sm-6 col-xs-12" id="metricsConf">CK Metricks, Scattering</label>
                                                </div>

                                                <div class="ln_solid"></div>
                                                <div class="row">
                                                    <label class="control-label col-md-3 col-sm-3 col-xs-12">Within/Cross</label>
                                                    <label class="control-label col-md-6 col-sm-6 col-xs-12" id="typeProjectConf">Cross/within</label>
                                                </div>

                                                <div class="ln_solid"></div>
                                                <div class="row">
                                                    <label class="control-label col-md-3 col-sm-3 col-xs-12">Projects(Cross-Project Prediction):</label>
                                                    <label class="control-label col-md-6 col-sm-6 col-xs-12" id="projectsConf">Project 1, 2</label>
                                                </div>

                                                <div class="ln_solid"></div>
                                                <div class="row">
                                                    <label class="control-label col-md-3 col-sm-3 col-xs-12">Classifier:</label>
                                                    <label class="control-label col-md-6 col-sm-6 col-xs-12" id="classifierConf">j48</label>
                                                </div>
                                               
                                                <div class="ln_solid"></div>-->
                                                <div class="row">
                                                    <label class="control-label col-md-12 col-sm-12 col-xs-12">WARNING: This operation can take a while</label>
                                                </div>
                                                
                                            </div>

                                            <div class="modal-footer">
                                                <button type="button" class="btn btn-default" onclick="onModalClose()" data-dismiss="modal">Close</button>
                                                <button type="submit" class="btn btn-success" >Confirm</button>
                                            </div>
                                        </div>

                                    </div>
                                </div>
                            </div>
                        </div>

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

<script src="scripts/parsleyjs/dist/parsley.js"></script>

<!-- Parsley -->
<script>

    isModalOnFocus = false;
//    isZip = false;
    function mySubmit() {
        if (true === $('#demo-form2').parsley().isValid()) {
            createModal();
            isModalOnFocus = true;
        }
    }
    function createModal() {
        $('#myModal').modal();
    }

    function onModalClose() {
        isModalOnFocus = false;
    }

    function onCrossPressed(aValue) {
        if (isCross && aValue === 'Within Project') {
            isCross = false;
            $('input[name="projects"]').iCheck('disable');
            $('input[name="projects"]').removeAttr('required');
            
        } else if (!isCross && aValue === 'Cross Project') {
            $('input[name="projects"]').iCheck('enable');
            isCross = true;
            $('input[name="projects"]').each( function() {
                if($('#github').val() !== ""){
                    gitLink = $('#github').val();
                    dirName = gitLink.split(".com/")[1].split(".git")[0];
                    splitted = dirName.split("/");
                    selectedProject = splitted[splitted.length - 1];
                    if(this.value === selectedProject) {
                        $(this).iCheck('disable');
                        $(this).iCheck('uncheck');
                    }
                }
            });
            $('input[name="projects"]').attr({
               'required' : 'required' 
            });
        }
    }

    $('input[name="typeProject"]').on('ifClicked', function(){
        onCrossPressed(this.value);
    });

    function changeProject(){
       $('input[name="typeProject"]').on('ifChecked', function(){
           typeProject = this.value;
       });
       if (typeProject === 'Within Project') {
            $('input[name="projects"]').iCheck('disable');
            $('input[name="projects"]').removeAttr('required');
        } else if (typeProject === 'Cross Project') {
            $('input[name="projects"]').iCheck('enable');
            $('input[name="projects"]').each( function() {
                if($('#github').val() !== ""){
                    gitLink = $('#github').val();
                    dirName = gitLink.split(".com/")[1].split(".git")[0];
                    splitted = dirName.split("/");
                    selectedProject = splitted[splitted.length - 1];
                    if(this.value === selectedProject) {
                        $(this).iCheck('disable');
                        $(this).iCheck('uncheck');
                    }
                }
            });
            $('input[name="projects"]').attr({
               'required' : 'required' 
            });
        }
    }
  
  
    $('#demo-form2').submit(function(){
        $('#myModal').modal('hide');
        onModalClose();
        $.blockUI({ message: '' });
        $.ajax({
            url: $(this).attr('action'),
            type: $(this).attr('method'),
            data: $(this).serialize(),
            dataType: 'json',
            success: function(result) {
                loadDivPrediction(result);
                $.unblockUI();
            }
        });
        return false;
    });
  
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
            'fixedColumns': true
        });
        $('html,body').animate({ scrollTop: $(document).height()-$(window).height() }, 'slow');        
        });
        
    }
    
    $(document).ready(function () {
        isCross = false;
        $.listen('parsley:field:validate', function () {
            validateFront();
        });

        $('button[name="confirm"]').on('click', function () {
            $('#demo-form2').parsley().validate();
            validateFront();
            if (!isModalOnFocus) {
                mySubmit();
            }
        });

        var validateFront = function () {
            if (true === $('#demo-form2').parsley().isValid()) {
                $('.bs-callout-warning').addClass('hidden');
                $('.bs-callout-info').removeClass('hidden');
            } else {
                $('.bs-callout-info').addClass('hidden');
                $('.bs-callout-warning').removeClass('hidden');
            }
        };
    });
    try {
        hljs.initHighlightingOnLoad();
    } catch (err) {
    }
</script>
<!-- /Parsley -->
