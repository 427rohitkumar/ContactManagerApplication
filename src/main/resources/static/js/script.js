// window.alert("hello")


const search=()=>{
    console.log("searching");

    let query=$("#search-input").val();

    if(query==""){
        $(".search-list-container").hide();
    }else{
    //    console.log(query);
       let url=`http://localhost:8080/search/${query}`;
       fetch(url).then((Response)=>{
         return Response.json();
       })
       .then((data)=>{
          console.log(data);
          let text=`<div class='list-group'>`;
          if(data.length>0){
            data.forEach(contact => {
                text +=`<div style='padding:5px;'><a href='/user/${contact.cId}/contact' class='list-group-item-a'><img class='profile-img' src='/images/${contact.image}'>${contact.name} </a></div>`
            });
          }else{
             text +=`<div style='padding:5px;'>No contact found</div>`;
          }
            
          text +=`</div>`;
          $(".search-list-container").html(text);
          $(".search-list-container").show();
       })
       .catch((error) => { 
           console.error('Error fetching data:', error); 
           $(".search-list-container").html("<div style='padding:5px;'>Error fetching data</div>"); 
           $(".search-list-container").show();
       });
      
    }
}