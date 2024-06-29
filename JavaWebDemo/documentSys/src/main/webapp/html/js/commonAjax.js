function  commonAjax(url, data, dataType, type, success, error){
    $.ajax({
        url:url,
        data:data,
        dataType:dataType,
        type:type,
        success: success,
        error:error
    })

}

