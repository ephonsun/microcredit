package banger.framework.util;

import java.util.Date;

/**
 * Created by XUElw on 2017/10/20.
 */
public class AnalogDataUtil {
    public static String base = "abcdefghijklmnopqrstuvwxyz0123456789";
    private static String[] firstName = "赵,钱,孙,李,周,吴,郑,王,冯,陈,褚,卫,蒋,沈,韩,杨,朱,秦,尤,许,何,吕,施,张,孔,曹,严,华,金,魏,陶,姜,戚,谢,邹,喻,柏,水,窦,章,云,苏,潘,葛,奚,范,彭,郎,鲁,韦,昌,马,苗,凤,花,方,俞,任,袁,柳,酆,鲍,史,唐,费,廉,岑,薛,雷,贺,倪,汤,滕,殷,罗,毕,郝,邬,安,常,乐,于,时,傅,皮,卞,齐,康,伍,余,元,卜,顾,孟,平,黄,和,穆,萧,尹,姚,邵,湛,汪,祁,毛,禹,狄,米,贝,明,臧,计,伏,成,戴,谈,宋,茅,庞,熊,纪,舒,屈,项,祝,董,梁,杜,阮,蓝,闵,席,季,麻,强,贾,路,娄,危,江,童,颜,郭,梅,盛,林,刁,钟,徐,邱,骆,高,夏,蔡,田,樊,胡,凌,霍,虞,万,支,柯,咎,管,卢,莫,经,房,裘,缪,干,解,应,宗,宣,丁,贲,邓,,郁,单,杭,,洪,包,诸,左,石,崔,吉,钮,龚,程,嵇,邢,滑,裴,陆,荣,翁,荀,羊,於,惠,甄,魏,加,封,芮,羿,储,靳,汲,邴,糜,松,井,段,富,巫,乌,焦,巴,弓,牧,隗,山,谷,车,侯,宓,蓬,全,郗,班,仰,秋,仲,伊,宫,宁,仇,栾,暴,甘,钭,厉,戎,祖,武,符,刘,姜,詹,束,龙,叶,幸,司,韶,郜,黎,蓟,薄,印,宿,白,怀,蒲,台,从,鄂,索,咸,籍,赖,卓,蔺,屠,蒙,池,乔,阴,郁,胥,能,苍,双,闻,莘,党,翟,谭,贡,劳,逄,姬,申,扶,堵,冉,宰,郦,雍,却,璩,桑,桂,濮,牛,寿,通,边,扈,燕,冀,郏,浦,尚,农,温,别,庄,晏,柴,瞿,阎,充,慕,连,茹,习,宦,艾,鱼,容,向,古,易,慎,戈,廖,庚,终,暨,居,衡,步,都,耿,满,弘,匡,国,文,寇,广,禄,阙,东,殴,殳,沃,利,蔚,越,夔,隆,师,巩,厍,聂,晁,勾,敖,融,冷,訾,辛,阚,那,简,饶,空,曾,毋,沙,乜,养,鞠,须,丰,巢,关,蒯,相,查,后,江,红,游,竺,权,逯,盖,益,桓,公,万俟,司马,上官,欧阳,夏侯,诸葛,闻人,东方,赫连,皇甫,尉迟,公羊,澹台,公冶,宗政,濮阳,淳于,仲孙,太叔,申屠,公孙,乐正,轩辕,令狐,钟离,闾丘,长孙,慕容,鲜于,宇文,司徒,司空,亓官,司寇,仉督,子车,颛孙,端木,巫马,公西,漆雕,乐正,壤驷,公良,拓拔,夹谷,宰父,谷粱,晋楚,阎法,汝鄢,涂钦,段干,百里,东郭,南门,呼延,归海,羊舌,微生,岳帅,缑亢,况后,有琴,梁丘,左丘,东门,西门,商牟,佘佴,伯赏,南宫,墨哈,谯笪,年爱,阳佟,第五,言福".split(",");
    private static String[] femaleName = "秀,娟,英,华,慧,巧,美,娜,静,淑,惠,珠,翠,雅,芝,玉,萍,红,娥,玲,芬,芳,燕,彩,春,菊,兰,凤,洁,梅,琳,素,云,莲,真,环,雪,荣,爱,妹,霞,香,月,莺,媛,艳,瑞,凡,佳,嘉,琼,勤,珍,贞,莉,桂,娣,叶,璧,璐,娅,琦,晶,妍,茜,秋,珊,莎,锦,黛,青,倩,婷,姣,婉,娴,瑾,颖,露,瑶,怡,婵,雁,蓓,纨,仪,荷,丹,蓉,眉,君,琴,蕊,薇,菁,梦,岚,苑,婕,馨,瑗,琰,韵,融,园,艺,咏,卿,聪,澜,纯,毓,悦,昭,冰,爽,琬,茗,羽,希,宁,欣,飘,育,滢,馥,筠,柔,竹,霭,凝,晓,欢,霄,枫,芸,菲,寒,伊,亚,宜,可,姬,舒,影,荔,枝,思,丽,".split(",");
    private static String[] maleName = "伟,刚,勇,毅,俊,峰,强,军,平,保,东,文,辉,力,明,永,健,世,广,志,义,兴,良,海,山,仁,波,宁,贵,福,生,龙,元,全,国,胜,学,祥,才,发,武,新,利,清,飞,彬,富,顺,信,子,杰,涛,昌,成,康,星,光,天,达,安,岩,中,茂,进,林,有,坚,和,彪,博,诚,先,敬,震,振,壮,会,思,群,豪,心,邦,承,乐,绍,功,松,善,厚,庆,磊,民,友,裕,河,哲,江,超,浩,亮,政,谦,亨,奇,固,之,轮,翰,朗,伯,宏,言,若,鸣,朋,斌,梁,栋,维,启,克,伦,翔,旭,鹏,泽,晨,辰,士,以,建,家,致,树,炎,德,行,时,泰,盛,雄,琛,钧,冠,策,腾,楠,榕,风,航,弘,".split(",");
    private static String[] address = "重庆大厦,黑龙江路,十梅庵街,遵义路,湘潭街,瑞金广场,仙山街,仙山东路,仙山西大厦,白沙河路,赵红广场,机场路,民航街,长城南路,流亭立交桥,虹桥广场,长城大厦,礼阳路,风岗街,中川路,白塔广场,兴阳路,文阳街,绣城路,河城大厦,锦城广场,崇阳街,华城路,康城街,正阳路,和阳广场,中城路,江城大厦,顺城路,安城街,山城广场,春城街,国城路,泰城街,德阳路,明阳大厦,春阳路,艳阳街,秋阳路,硕阳街,青威高速,瑞阳街,丰海路,双元大厦,惜福镇街道,夏庄街道,古庙工业园,中山街,太平路,广西街,潍县广场,博山大厦,湖南路,济宁街,芝罘路,易州广场,荷泽四路,荷泽二街,荷泽一路,荷泽三大厦,观海二广场,广西支街,观海一路,济宁支街,莒县路,平度广场,明水路,蒙阴大厦,青岛路,湖北街,江宁广场,郯城街,天津路,保定街,安徽路,河北大厦,黄岛路,北京街,莘县路,济南街,宁阳广场,日照街,德县路,新泰大厦,荷泽路,山西广场,沂水路,肥城街,兰山路,四方街,平原广场,泗水大厦,浙江路,曲阜街,寿康路,河南广场,泰安路,大沽街,红山峡支路,西陵峡一大厦,台西纬一广场,台西纬四街,台西纬二路,西陵峡二街,西陵峡三路,台西纬三广场,台西纬五路,明月峡大厦,青铜峡路,台西二街,观音峡广场,瞿塘峡街,团岛二路,团岛一街,台西三路,台西一大厦,郓城南路,团岛三街,刘家峡路,西藏二街,西藏一广场,台西四街,三门峡路,城武支大厦,红山峡路,郓城北广场,龙羊峡路,西陵峡街,台西五路,团岛四街,石村广场,巫峡大厦,四川路,寿张街,嘉祥路,南村广场,范县路,西康街,云南路,巨野大厦,西江广场,鱼台街,单县路,定陶街,滕县路,钜野广场,观城路,汶上大厦,朝城路,滋阳街,邹县广场,濮县街,磁山路,汶水街,西藏路,城武大厦,团岛路,南阳街,广州路,东平街,枣庄广场,贵州街,费县路,南海大厦,登州路,文登广场,信号山支路,延安一街,信号山路,兴安支街,福山支广场,红岛支大厦,莱芜二路,吴县一街,金口三路,金口一广场,伏龙山路,鱼山支街,观象二路,吴县二大厦,莱芜一广场,金口二街,海阳路,龙口街,恒山路,鱼山广场,掖县路,福山大厦,红岛路,常州街,大学广场,龙华街,齐河路,莱阳街,黄县路,张店大厦,祚山路,苏州街,华山路,伏龙街,江苏广场,龙江街,王村路,琴屿大厦,齐东路,京山广场,龙山路,牟平街,延安三路,延吉街,南京广场,东海东大厦,银川西路,海口街,山东路,绍兴广场,芝泉路,东海中街,宁夏路,香港西大厦,隆德广场,扬州街,郧阳路,太平角一街,宁国二支路,太平角二广场,天台东一路,太平角三大厦,漳州路一路,漳州街二街,宁国一支广场,太平角六街,太平角四路,天台东二街,太平角五路,宁国三大厦,澳门三路,江西支街,澳门二路,宁国四街,大尧一广场,咸阳支街,洪泽湖路,吴兴二大厦,澄海三路,天台一广场,新湛二路,三明北街,新湛支路,湛山五街,泰州三广场,湛山四大厦,闽江三路,澳门四街,南海支路,吴兴三广场,三明南路,湛山二街,二轻新村镇,江南大厦,吴兴一广场,珠海二街,嘉峪关路,高邮湖街,湛山三路,澳门六广场,泰州二路,东海一大厦,天台二路,微山湖街,洞庭湖广场,珠海支街,福州南路,澄海二街,泰州四路,香港中大厦,澳门五路,新湛三街,澳门一路,正阳关街,宁武关广场,闽江四街,新湛一路,宁国一大厦,王家麦岛,澳门七广场,泰州一路,泰州六街,大尧二路,青大一街,闽江二广场,闽江一大厦,屏东支路,湛山一街,东海西路,徐家麦岛函谷关广场,大尧三路,晓望支街,秀湛二路,逍遥三大厦,澳门九广场,泰州五街,澄海一路,澳门八街,福州北路,珠海一广场,宁国二路,临淮关大厦,燕儿岛路,紫荆关街,武胜关广场,逍遥一街,秀湛四路,居庸关街,山海关路,鄱阳湖大厦,新湛路,漳州街,仙游路,花莲街,乐清广场,巢湖街,台南路,吴兴大厦,新田路,福清广场,澄海路,莆田街,海游路,镇江街,石岛广场,宜兴大厦,三明路,仰口街,沛县路,漳浦广场,大麦岛,台湾街,天台路,金湖大厦,高雄广场,海江街,岳阳路,善化街,荣成路,澳门广场,武昌路,闽江大厦,台北路,龙岩街,咸阳广场,宁德街,龙泉路,丽水街,海川路,彰化大厦,金田路,泰州街,太湖路,江西街,泰兴广场,青大街,金门路,南通大厦,旌德路,汇泉广场,宁国路,泉州街,如东路,奉化街,鹊山广场,莲岛大厦,华严路,嘉义街,古田路,南平广场,秀湛路,长汀街,湛山路,徐州大厦,丰县广场,汕头街,新竹路,黄海街,安庆路,基隆广场,韶关路,云霄大厦,新安路,仙居街,屏东广场,晓望街,海门路,珠海街,上杭路,永嘉大厦,漳平路,盐城街,新浦路,新昌街,高田广场,市场三街,金乡东路,市场二大厦,上海支路,李村支广场,惠民南路,市场纬街,长安南路,陵县支街,冠县支广场,小港一大厦,市场一路,小港二街,清平路,广东广场,新疆路,博平街,港通路,小港沿,福建广场,高唐街,茌平路,港青街,高密路,阳谷广场,平阴路,夏津大厦,邱县路,渤海街,恩县广场,旅顺街,堂邑路,李村街,即墨路,港华大厦,港环路,馆陶街,普集路,朝阳街,甘肃广场,港夏街,港联路,陵县大厦,上海路,宝山广场,武定路,长清街,长安路,惠民街,武城广场,聊城大厦,海泊路,沧口街,宁波路,胶州广场,莱州路,招远街,冠县路,六码头,金乡广场,禹城街,临清路,东阿街,吴淞路,大港沿,辽宁路,棣纬二大厦,大港纬一路,贮水山支街,无棣纬一广场,大港纬三街,大港纬五路,大港纬四街,大港纬二路,无棣二大厦,吉林支路,大港四街,普集支路,无棣三街,黄台支广场,大港三街,无棣一路,贮水山大厦,泰山支路,大港一广场,无棣四路,大连支街,大港二路,锦州支街,德平广场,高苑大厦,长山路,乐陵街,临邑路,嫩江广场,合江路,大连街,博兴路,蒲台大厦,黄台广场,城阳街,临淄路,安邱街,临朐路,青城广场,商河路,热河大厦,济阳路,承德街,淄川广场,辽北街,阳信路,益都街,松江路,流亭大厦,吉林路,恒台街,包头路,无棣街,铁山广场,锦州街,桓台路,兴安大厦,邹平路,胶东广场,章丘路,丹东街,华阳路,青海街,泰山广场,周村大厦,四平路,台东西七街,台东东二路,台东东七广场,台东西二路,东五街,云门二路,芙蓉山村,延安二广场,云门一街,台东四路,台东一街,台东二路,杭州支广场,内蒙古路,台东七大厦,台东六路,广饶支街,台东八广场,台东三街,四平支路,郭口东街,青海支路,沈阳支大厦,菜市二路,菜市一街,北仲三路,瑞云街,滨县广场,庆祥街,万寿路,大成大厦,芙蓉路,历城广场,大名路,昌平街,平定路,长兴街,浦口广场,诸城大厦,和兴路,德盛街,宁海路,威海广场,东山路,清和街,姜沟路,雒口大厦,松山广场,长春街,昆明路,顺兴街,利津路,阳明广场,人和路,郭口大厦,营口路,昌邑街,孟庄广场,丰盛街,埕口路,丹阳街,汉口路,洮南大厦,桑梓路,沾化街,山口路,沈阳街,南口广场,振兴街,通化路,福寺大厦,峄县路,寿光广场,曹县路,昌乐街,道口路,南九水街,台湛广场,东光大厦,驼峰路,太平山,标山路,云溪广场,太清路".split(",");
    private static final String[] emailSuffix = "@gmail.com,@yahoo.com,@msn.com,@hotmail.com,@aol.com,@ask.com,@live.com,@qq.com,@0355.net,@163.com,@163.net,@263.net,@3721.net,@yeah.net,@googlemail.com,@126.com,@sina.com,@sohu.com,@yahoo.com.cn".split(",");
    private static String[] custLevel = {"黄金","白金", "铂金", "钻石"};
    private static String[] custDegree = {"小学", "初中", "中专", "高中", "大专", "本科", "硕士", "博士"};
    private static String[] identifyType = "居民身份证,台湾居民来往大陆通行证,港澳居民来往内地通行证(香港),港澳居民来往内地通行证(澳门),临时身份证,护照,户口簿,军人身份证,武装警察身份证,外交人员身份证,外国人居留许可证,边民出入境通行证,其他".split(",");
    private static String[] nativePlace = "北京市,天津市,上海市,重庆市,河北省,山西省,辽宁省,吉林省,黑龙江省,江苏省,浙江省,安徽省,福建省,江西省,山东省,河南省,湖北省,湖南省,广东省,海南省,四川省,贵州省,云南省,陕西省,甘肃省,青海省,台湾省,内蒙古自治区,广西壮族自治区,西藏自治区,宁夏回族自治区,新疆维吾尔自治区,香港特别行政区,澳门特别行政区".split(",");
    private static String[] maritalStatus = {"已婚","未婚","离异", "丧偶"};
    private static String[] decoration = {"毛坯", "普通装修", "精装", "豪华装修", "其他"};
    private static String[] houseLayout = {"单元式住宅","公寓式住宅","复式住宅","跃层式住宅","花园洋房式住宅","小户型住宅"};
    private static String[] monthIncome = {"3000以下","3000-5000","5000-8000", "8000-11000","11000-14000","14000以上"};

    private static String gender = "";

    /**
     * 随机数字
     *
     * @param start 起始数字
     * @param end   结束数字
     * @return
     */
    public static int getRandomNum(int start, int end) {
        return (int) (Math.random() * (end - start + 1) + start);
    }

    /**
     * 返回Email
     *
     * @param lMin 最小长度
     * @param lMax 最大长度
     * @return
     */
    public static String getEmail(int lMin, int lMax) {
        int length = getRandomNum(lMin, lMax);
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = (int) (Math.random() * base.length());
            sb.append(base.charAt(number));
        }
        sb.append(emailSuffix[(int) (Math.random() * emailSuffix.length)]);
        return sb.toString();
    }

    /**
     * 返回手机号码
     */
    private static String[] telFirst = "134,135,136,137,138,139,150,151,152,157,158,159,130,131,132,155,156,133,153".split(",");

    private static String getPhone() {
        int index = getRandomNum(0, telFirst.length - 1);
        String first = telFirst[index];
        String second = String.valueOf(getRandomNum(1, 888) + 10000).substring(1);
        String third = String.valueOf(getRandomNum(1, 9100) + 10000).substring(1);
        return first + second + third;
    }

    /**
     * 返回中文姓名
     */
    private static String getName() {
        String firstname = getArrayData(firstName);
        String secondname = "";
        int sex = getRandomNum(0, 1);
        if (sex == 0) {
            secondname = getArrayData(femaleName);
            secondname = secondname + getArrayData(femaleName);
            gender = "女";
        } else {
            secondname = getArrayData(maleName);
            secondname = secondname + getArrayData(maleName);
            gender = "男";
        }
        return firstname + secondname;
    }

    /**
     * 随机获取数组中值
     *
     * @return
     */
    private static String getArrayData(String[] array) {
        int index = getRandomNum(0, array.length - 1);
        String data = array[index];
        return data;
    }


    /**
     * 数据封装
     *
     * @return
     */
    public static StringBuffer getAnalogData(Integer id) {
        StringBuffer buffer = new StringBuffer();
        //个人信息
        buffer.append(id+",");
        buffer.append(getName()+",");
        buffer.append(getArrayData(custLevel)+ ",");
        buffer.append(gender+",");
        buffer.append(getRandomNum(18,60)+",");
        buffer.append(getArrayData(custDegree)+",");
        buffer.append(getPhone()+",");
        buffer.append(getArrayData(identifyType)+",");
        buffer.append(IdCardGenerator.generate()+",");
        buffer.append(getArrayData(nativePlace)+",");
        buffer.append(getArrayData(address)+",");
        buffer.append(getArrayData(address)+",");
        buffer.append(getArrayData(maritalStatus)+",");
        //配偶信息
        buffer.append(getName()+",");
        buffer.append(gender+",");
        buffer.append(getRandomNum(18,60)+",");
        buffer.append(getPhone()+",");
        buffer.append(getArrayData(identifyType)+",");
        buffer.append(IdCardGenerator.generate()+",");
        buffer.append(getArrayData(custDegree)+",");
        buffer.append(getArrayData(monthIncome)+",");

        //家庭信息
        buffer.append(getArrayData(address)+",");
        buffer.append(getRandomNum(100,200)+",");
        buffer.append("2015-10-20 09:13:30,");
        buffer.append(getArrayData(decoration)+",");
        buffer.append(getArrayData(houseLayout)+",");
        buffer.append(getRandomNum(1,10)+",");
        buffer.append(getRandomNum(1,6)+",");
        buffer.append("000000,");
        buffer.append("000000,");
        buffer.append(0+",");
        buffer.append(0+",");
        buffer.append("K"+IdCardGenerator.generate()+",");
        return buffer;
    }

    /*public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            System.out.println(getAnalogData(i + 1).toString());
        }
    }*/
}
