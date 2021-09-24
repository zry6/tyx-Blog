function show_date_time(){
    window.setTimeout("show_date_time()", 1000);
    BirthDay=new Date("9/16/2021 03:30:06");
    today=new Date();
    timeold=(today.getTime()-BirthDay.getTime());
    sectimeold=timeold/1000
    secondsold=Math.floor(sectimeold);
    msPerDay=24*60*60*1000
    e_daysold=timeold/msPerDay
    daysold=Math.floor(e_daysold);
    e_hrsold=(e_daysold-daysold)*24;
    hrsold=Math.floor(e_hrsold);
    e_minsold=(e_hrsold-hrsold)*60;
    minsold=Math.floor((e_hrsold-hrsold)*60);
    seconds=Math.floor((e_minsold-minsold)*60);
    span_dt_dt.innerHTML='<font style=color:#FF0000>'+daysold+'</font> 天 <font style=color:#FF0000>'+hrsold+'</font> 时 <font style=color:#FF0000>'+minsold+'</font> 分 <font style=color:#FF0000>'+seconds+'</font> 秒';
}
