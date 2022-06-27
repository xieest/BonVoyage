//Get data from local storage
storage = JSON.parse(localStorage.getItem("products"))
const _s=(a) => document.querySelector(a)
let total = 0
let tax = 0.06
let subtotal = 0

let pc = _s("#product-container") 

//If data doesn't exist
if (storage == null) {
    pc.innerHTML = "Please Add A Product"
}

//If data does exist
else {
    //Update counter
    _s(".counter").innerHTML = storage.length
    let pc = _s("#product-container") 

    //Loop through all the IDs in the data array
    storage.forEach(p => {

    //Get each product that matches the ID
    let pd = products.filter(a => a.id == p) 

    //Create a new div in the DOM for each single product
    let pr = document.createElement("div")
    subtotal += pd[0].price

    //Insert HTML for each product from the product object
    pr.innerHTML = `
    <div>
    <img src="${pd[0].img}" alt="">
    </div>
    <div>
    <h5><span class="title">${pd[0].name}</span><span class="price">$${pd[0].price}</span></h5>
    <div>
        <span class="sm-price">$${pd[0].price}</span>
    </div>
    <div class="flex justify-sb">
        <span>
        </span>
        <button class="delete flex a-i-c" data-id = "${pd[0].id}"><img src="https://img.icons8.com/material/24/000000/filled-trash.png"/><span>Delete</span></button>
    </div>   
    </div>`
    pr.classList.add("pr")

    //Append each product to a container in the DOM
    pc.prepend(pr)
   });
}
    //Calculations for total
    total = Math.round(subtotal * tax)
    _s("span.total-price").innerHTML = `$${total + subtotal}`
    _s("span.subtotal").innerHTML = "$" + subtotal
    _s("span.tax").innerHTML = "$" + total

    //Get all delete buttons and add event listeners
    let r = document.querySelectorAll(".delete")
    r.forEach(g=> {
        g.addEventListener("click", e=> {
            let id = e.target.dataset.id
            //Remove the parent container of the product from the DOM
            e.target.closest(".pr").remove() 

            //Remove the product ID from the storage
            let products = storage.filter(a=>a.id != id) 
            storage.splice(storage.indexOf(id), 1)
            //Update the local storage 
            localStorage.setItem("products", JSON.stringify(storage))
            //Reupdate the counter
            _s(".counter").innerHTML = storage.length
        })
    })