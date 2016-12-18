<%@page import="it.unisa.gitdm.bean.ProjectCross"%>
<%@page import="it.unisa.primeLab.ProjectCrossHandler"%>
<%@page import="java.util.ArrayList"%>
    
<% ArrayList<ProjectCross> projects = ProjectCrossHandler.getAllProjects(); %>

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
                    <h2>Calculate new prediction </h2>

                    <div class="clearfix"></div>
                </div>
                <div class="x_content" style="display: block;">
                    <br>
                    <form action="BuildModelServlet" method="POST" id="demo-form2" data-parsley-validate="" class="form-horizontal form-label-left" novalidate="">

                        <div class="form-group">
                            <label class="control-label col-md-3 col-sm-3 col-xs-12" for="first-name">Github link <span class="required">*</span>
                            </label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <input type="text" name="github" id="github" required="required" class="form-control col-md-7 col-xs-12">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-md-3 col-sm-3 col-xs-12" for="last-name">Issue Tracker <span class="required">*</span>
                            </label>
                            <div  class="col-md-6 col-sm-6 col-xs-12">
                                <div class="col-md-3"></div>
                                <div class="col-md-3">
                                    <input type="radio" name="issueTracker" class="flat" value="https://issues.apache.org/bugzilla/" checked="" required />
                                    Bugzilla
                                </div>

                                <div class="col-md-3">
                                    <input type="radio" class="flat" name="issueTracker" value="https://issues.apache.org/jira/" />
                                    Jira
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
                                <!--                                <div class="col-md-3">
                                                                    <div class="checkbox">
                                                                        <label class="">
                                                                            <input type="checkbox" name="metrics" value="Metrica 4" required="required" class="flat" checked="checked"> Metrica 4
                                                                        </label>
                                                                    </div>
                                                                </div>-->
                            </div>
                        </div>

                        <div class="ln_solid"></div>
                        <div class="form-group">
                            <label class="control-label col-md-3 col-sm-3 col-xs-12">Within/Cross Project *</label>
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
                            <label class="control-label col-md-3 col-sm-3 col-xs-12" id="crossLabel">Project Selection *</label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <% for (ProjectCross c : projects) {
                                        out.print("<div class=\"col-md-4\">");
                                        out.print("<div class=\"checkbox\">");
                                        out.print("<label>");
                                        out.print("<input type=\"checkbox\" name=\"projects\"");
                                        out.print("disabled='disabled'");
                                        out.print("class=\"flat\" value=\"" + c.getName() + "\" required=\"required\"");
                                        out.print("checked='checked'");
                                        out.print("> " + c.getName());
                                        out.print("</label>");
                                        out.print("</div>");
                                        out.print("</div>");
                                    }
                                %> 
                                <div id="newChecks">
                                    
                                </div>
                                <div class="col-md-offset-12">
                                    <button data-toggle="modal" href="#modalProject" id="buttonAdd" type="button" disabled="disabled" class="btn btn-default">Add New Project</button>
                                </div>
                                <div id="modalProject" class="modal fade bs-example-modal-lg" tabindex="-1" role="dialog" aria-hidden="true">
                                    <div class="modal-dialog modal-lg">
                                        <div class="modal-content">

                                            <div class="modal-header">
                                                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span>
                                                </button>
                                                <h4 class="modal-title" id="myModalLabel">Select how to add a Project</h4>
                                            </div>
                                            <div class="modal-body">
                                                <div class="row">
                                                    <label class="control-label col-md-3 col-sm-3 col-xs-12">
                                                        <input type="radio" class="flat" name="addType" value="1">
                                                        From link: </label>
                                                    <input id="linkPath" type="text" disabled="disabled" placeholder="Insert the link for the project" class="col-md-6 col-sm-6 col-xs-12">
                                                </div>
                                                <div class="ln_solid"></div>
                                                <div class="row">
                                                    <label class="control-label col-md-3 col-sm-3 col-xs-12">
                                                        <input type="radio" class="flat" name="addType" value="2">
                                                        From zip: </label>
                                                    <input id="zipPath" type="file" disabled="disabled" accept="application/.zip" class="col-md-6 col-sm-6 col-xs-12">
                                                </div>
                                            </div>
                                            <div class="modal-footer">
                                                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                                                <button type="button" onclick="confirmModal()" class="btn btn-success" data-dismiss="modal">Confirm</button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="ln_solid"></div>
                        <div class="form-group">
                            <label class="control-label col-md-3 col-sm-3 col-xs-12">Classifier *</label>
                            <div class="col-md-3 col-sm-6 col-xs-12">
                                <select id="classifier" name="classifier" class="form-control" required="">
                                    <option value="">Choose...</option>
                                    <option value="Decision Table Majority">Decision Table Majority</option>
                                    <option value="Logistic Regression">Logistic Regression</option>
                                    <option value="Multi Layer Perceptron">Multi Layer Perceptron</option>
                                    <option value="Naive Baesian">Naive Baesian</option>
                                    <option value="Random Forest">Random Forest</option>
                                </select>
                            </div>
                            <div class="col-md-3 col-sm-6 col-xs-12 col-md-offset-3">
                                <button type="reset" class="btn btn-danger">Cancel</button>
                                <button type="button" name="confirm" class="btn btn-success">Submit</button>
                                <div id="myModal" class="modal fade bs-example-modal-lg" tabindex="-1" role="dialog" aria-hidden="true">
                                    <div class="modal-dialog modal-lg">
                                        <div class="modal-content">

                                            <div class="modal-header">
                                                <button type="button" onclick="onModalClose()" class="close" data-dismiss="modal"><span aria-hidden="true">×</span>
                                                </button>
                                                <h4 class="modal-title" id="myModalLabel">Confirm your data</h4>
                                            </div>
                                            <div class="modal-body">

                                                <div class="row">
                                                    <label class="control-label col-md-3 col-sm-3 col-xs-12">Github link: </label>
                                                    <label class="control-label col-md-6 col-sm-6 col-xs-12" id="githubConf">link</label>
                                                </div>

                                                <div class="ln_solid"></div>
                                                <div class="row">
                                                    <label class="control-label col-md-3 col-sm-3 col-xs-12">Issue Tracker: </label>
                                                    <label class="control-label col-md-6 col-sm-6 col-xs-12" id="issueTrackerConf">issue tracker name</label>
                                                </div>

                                                <div class="ln_solid"></div>
                                                <div class="row">
                                                    <label class="control-label col-md-3 col-sm-3 col-xs-12">Issue tracker URL: </label>
                                                    <label class="control-label col-md-6 col-sm-6 col-xs-12" id="issueTrackerURLConf">issue tracker name</label>
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
                                                    <label class="control-label col-md-3 col-sm-3 col-xs-12">Projects:</label>
                                                    <label class="control-label col-md-6 col-sm-6 col-xs-12" id="projectsConf">Project 1, 2</label>
                                                </div>

                                                <div class="ln_solid"></div>
                                                <div class="row">
                                                    <label class="control-label col-md-3 col-sm-3 col-xs-12">Classifier:</label>
                                                    <label class="control-label col-md-6 col-sm-6 col-xs-12" id="classifierConf">j48</label>
                                                </div>
                                                <div class="ln_solid"></div>
                                                <div class="ln_solid"></div>
                                                <div class="ln_solid"></div>
                                                <div class="row">
                                                    <label class="control-label col-md-12 col-sm-3 col-xs-12">Please enter your e-mail address to be alerted when the results will be available: </label>
                                                </div>
                                                <br>
                                                <div class="row">
                                                    <div class = "col-md-6 col-sm-3 col-xs-12"></div>
                                                    <div class = "col-md-6 col-sm-3 col-xs-12" id="bottomModal">

                                                    </div>
                                                </div>

                                            </div>

                                            <div class="modal-footer">
                                                <button type="button" class="btn btn-default" onclick="onModalClose()" data-dismiss="modal">Close</button>
                                                <button type="submit"name="confirm" class="btn btn-success">Confirm</button>
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


    <!-- /CONTENT -->
</div>


<!-- /page content -->

<!-- footer content -->
<jsp:include page="footer.jsp" />

<!-- iCheck -->
<script src="scripts/icheck.js"></script>
<script src="scripts/parsleyjs/dist/parsley.js"></script>

<!-- Parsley -->
<script>

    isModalOnFocus = false;
    isZip = false;
    function mySubmit() {
        if (true === $('#demo-form2').parsley().isValid()) {
            createModal();
            isModalOnFocus = true;
        }
    }
    function createModal() {
        document.getElementById("githubConf").innerHTML = $('#github').val();
        document.getElementById("issueTrackerURLConf").innerHTML = $('input[name="issueTracker"]:checked').val();
        if ($('input[name="issueTracker"]:checked').val() === "https://issues.apache.org/bugzilla/") {
            document.getElementById("issueTrackerConf").innerHTML = "Bugzilla";
        } else {
            document.getElementById("issueTrackerConf").innerHTML = "Jira";
        }
        var metrics = document.getElementById("metricsConf");
        metrics.innerHTML = '';
        $('input[name="metrics"]').each(function () {
            metrics.innerHTML += (this.checked ? $(this).val() + "; " : "");
        });
        document.getElementById("typeProjectConf").innerHTML = $('input[name="typeProject"]:checked').val();
        if (isCross) {
            var projects = document.getElementById("projectsConf");
            projects.innerHTML = '';
            $('input[name="projects"]').each(function () {
                projects.innerHTML += (this.checked ? $(this).val() + "; " : "");
            });
        } else {
            document.getElementById("projectsConf").innerHTML = 'None';
        }
        document.getElementById("classifierConf").innerHTML = $('#classifier').val();
        document.getElementById("bottomModal").innerHTML = '<input type = "email" id = "email"  required="required" class = "form-control" >';
        $('#myModal').modal();
    }

    function onModalClose() {
        isModalOnFocus = false;
        document.getElementById("bottomModal").innerHTML = '';
    }
                                      
    function confirmModal(){
        if(isZip) {
            fullZipPath = $('#zipPath').val();
            zipFile = fullZipPath.substring(12,fullZipPath.length-4);
            if(fullZipPath !== ""){
                
                
                
                $('#newChecks').append('<div class="col-md-4"><div class="checkbox"><label><input type="checkbox" name="projects" class="flat" value="' + fullZipPath + '" required="required" checked="checked" data-parsley-multiple="projects"> ' + zipFile + '</label></div></div>');
                $('input[name="projects"]').iCheck({
                    checkboxClass: 'icheckbox_flat-green'
                });
            }
        } else {
            fullLink = $('#linkPath').val();
            linkFile = fullLink.substring(26, fullLink.length-4);
            if(fullLink !== ""){
                
                
                
                $('#newChecks').append('<div class="col-md-4"><div class="checkbox"><label><input type="checkbox" name="projects" class="flat" value="' + fullLink + '" required="required" checked="checked" data-parsley-multiple="projects"> ' + linkFile + '</label></div></div>');
                $('input[name="projects"]').iCheck({
                    checkboxClass: 'icheckbox_flat-green'
                });
            }
        }
    }

    function onCrossPressed(aValue) {
        if (isCross && aValue === 'Within Project') {
            isCross = false;
            $('input[name="projects"]').iCheck('disable');
            $('#buttonAdd').attr({
                'disabled': 'disabled'
            });
            
        } else if (!isCross && aValue === 'Cross Project') {
            isCross = true;
            $('input[name="projects"]').iCheck('enable');
            $('#buttonAdd').removeAttr('disabled');    
        }
    }

    $('input[name="typeProject"]').on('ifClicked', function () {
        onCrossPressed(this.value);
    });

    $('input[name="addType"]').on('ifClicked', function () {
        if (this.value === '1') {
            isZip = false;
            $('#zipPath').attr({
                'disabled': 'disabled'
            });
            $('#linkPath').removeAttr('disabled');
        } else if (this.value === '2') {
            isZip = true;
            $('#linkPath').attr({
                'disabled': 'disabled'
            });
            $('#zipPath').removeAttr('disabled');
        }
    });

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
