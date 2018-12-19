function downHelp() {
    window.location="../desktop/downloadHelpFile.html?random="+Math.random()*100000;
}

function downTray(){
    window.location="../upgrade/downloadSetupFile.html?random="+Math.random()*100000;
}

function downResourceFile(fileName){
    var url = "../desktop/downloadResources.html?fileName="+encodeURI(encodeURI(fileName))+"&random="+Math.random()*100000;
    window.location.href = url;
}