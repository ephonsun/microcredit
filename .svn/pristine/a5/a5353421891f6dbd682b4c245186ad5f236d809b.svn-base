//省市联动
areaLinkage = function(){
    //省市联动
    var optionProvince = {
        url:'../system/queryAreaListData.html?type=province',
        ajaxSearch: true,//异步查询,
        keyWordParam: 'k',//模糊查询查询字符串
        type:'get',//ajax的type
        readonly:true,//可编辑输入框
        addBlankOption: true//是否添加一个空选项,
    };
    var optionCity = {
        url:'../system/queryAreaListData.html?type=city&cityCode=',
        readonly: true,
        ajaxSearch: true,
        type: 'get',
        addBlankOption: true
    };
    var optionCounty = {
        url:'../system/queryAreaListData.html?type=county&cityCode=',
        readonly: true,
        ajaxSearch: false,
        addBlankOption: true
    };
    var $provinceSlts = $('select[data-type="province"]');
    var $citySlts = $('select[data-type="city"]');
    //初始化省市县
    $provinceSlts.each(function(){
        var $province = $(this);
        var $city = $($province.data('city'));
        var $county = $($province.data('county'));
        var $address = $($city.data('address'));
        var extOptionProvince = {
            url: optionProvince.url,
            value: ''
        };
        var extOptionCity = {
            url: optionCity.url,
            value: ''
        };        
        var extOptionCounty = {
            url: optionCounty.url,
            value: ''
        };

        var provinceValue = getChosenOption($province);
        if( provinceValue ) {
            extOptionProvince.value = provinceValue;
            extOptionCity.url = extOptionCity.url + provinceValue;
        } else {
            extOptionCity.url = '';
            extOptionCity.addBlankOption = false;
            $city.attr('disabled', true);
        }

        var cityValue = getChosenOption($city);
        if( cityValue ) {
            extOptionCity.value = cityValue;
            extOptionCounty.url = extOptionCounty.url + cityValue;
            if($address.length) {
                $address.attr('disabled', false);
            }
        }  else {
            extOptionCounty.url = '';
            extOptionCounty.addBlankOption = false;
            $county.attr('disabled', true);
        }

        var countyValue = getChosenOption($county);
        if( countyValue ) {
            extOptionCounty.value = countyValue;
        }

        $province.selectbox($.extend({},optionProvince,extOptionProvince));
        $city.selectbox($.extend({},optionCity,extOptionCity));
        $county.selectbox($.extend({},optionCounty,extOptionCounty));
    });
    //得到select中的option的值
    function getChosenOption($select){
        //假如省市县有option选中
        if( $select.children().length && $select.find('option:selected').length ) {
            return $select.find('option:selected').val();
        }
        return false;
    }
    //省份改变的时候
    $provinceSlts.change(function(e){
        var changeUrl = {
            url:optionCity.url+$(this).find('option:selected').val()
        };
        var city = $(this).data('city');
        var county = $(this).data('county');
        var address = $(this).data('address');
        if($(this).find('option:selected').val() === '') {
            $(city).empty();
            $(city).data('ns').find(':text').attr('disabled',true);

            $(county).empty();
            $(county).data('ns').find(':text').attr('disabled',true);
            if(address) {
                $(address).attr('disabled',true);
            }
        } else {
            $(city).removeAttr('disabled');
            $(city).selectbox($.extend({},optionCity,changeUrl));
        }
        $(city).data('ns').find(':text').val('');
        $(county).data('ns').find(':text').val('');
    });
    //城市改变的时候
    $citySlts.change(function(e){
        var changeUrl = {
            url:optionCounty.url+$(this).find('option:selected').val()
        };
        var county = $(this).data('county');
        var address = $(this).data('address');
        if($(this).find('option:selected').val() === '') {
            $(county).empty();
            $(county).data('ns').find(':text').val('');
            if(address) {
                $(address).attr('disabled',true);
            }
        } else {
            $(county).removeAttr('disabled');
            $(county).selectbox($.extend({},optionCity,changeUrl));
            if(address) {
                $(address).attr('disabled',false);
            }
        }
    });
};


