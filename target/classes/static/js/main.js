//Function to return an element from the DOM
const _s=(a) => document.querySelector(a)
let filtered = []

function isInArray(value, array) {
    return array.indexOf(value) > -1;
  }
  
let productcontainer = _s("#product-parent") 
let storage = []

//Get the storage
storage = JSON.parse(localStorage.getItem("products"))
//If storage doesn't exist, then create it
if(storage === null) storage = localStorage.setItem("products", JSON.stringify([]))
//If storage exists, update the counter
else {
    _s(".counter").innerHTML = storage.length
}
//Function to update product IDs to the storage
const addToCart = (id) => {
    //Remove ID from the storage 
    if (storage.length != 0 && isInArray(id, storage)) { storage.splice(storage.indexOf(id),1); 
        let removed = products.find(a=>a.id == id)
    _s(".add span").innerHTML = "$"+removed.price+" - Add to Cart"}

    //If the ID doesn't exist in the storage, then add it 
    else {
        storage.push(id) 
    }
    //Update the local storage
    localStorage.setItem("products", JSON.stringify(storage))
}

//Function to append each single product to the DOM
function ap(productsArr) { 
    //Loop through all IDs in the storage
    productcontainer.innerHTML = ""
    productsArr.forEach(element => {
    let p = document.createElement("div")
    //Update each product data 
    p.innerHTML = `
    <div class = "s-card">
                <div class = "s-image">
                    <img src = "${element.img}" alt = "">
                </div>
                <div class = "s-text">
                    <h5>${element.name}</h5>
                    <span class = "price">$${element.price}</span>
                </div>
            </div>
            ` ;
            //Append the products to the DOM
            productcontainer.appendChild(p)
            
            //Add event listner to the products to open up a popup containing more information
            p.addEventListener("click", e=>{
            //Trigger the popup and update its content
            _s(".side-img").setAttribute("src", element.img)
            _s(".right").classList.add("active")
            
            //Shows price
            _s(".side-content h2").textContent=element.name
            _s(".side-content p").textContent=element.description
            _s(".add span").textContent="$"+element.price+" - Add to Cart"

            //Update the cart button based on the products availibility in the storage
            if(storage.length > 0 && storage.includes(element.id)) _s(".add span").textContent="Added"
            let pid = _s(".add span").dataset.id=element.id
            })
});
}
//On add to cart, update the cart button and the counter
_s(".add span").addEventListener("click", e => {
    let cart = [...storage]
    e.target.textContent = "added"
    _s(".add").classList.add("remove")
    let id = e.target.dataset.id;
    //Pass the product ID to the add to cart function
    addToCart(parseInt(id))
    _s(".counter").innerHTML = storage.length
})




let search = _s("#search")
//On input, filter through all products and get a matching character
search.addEventListener("input", (e) => {
    let word = search.value 
    let found = products.filter(w => {return w.name.toLowerCase().indexOf(word.toLowerCase()) > -1})
    //Only show the found product in the DOM
    ap(found)
}) 

//Initialize the show all products function when the page loads
ap(products)

//Close the popup after clicking on the X
_s(".close").addEventListener("click", e=> {
    _s(".right").classList.remove("active")
})

//Get all filters and loop through them
let filters = document.querySelectorAll(".filters input")
filters.forEach(f => {
    //On check
    f.addEventListener("input", e=> {
        let c = f.dataset.category
        if (e.target.checked) {
            //If checked, get all matching products
            let found = products.filter(w => {return w.category.toLowerCase().indexOf(c.toLowerCase()) > -1})
            filtered.push(...found)
        }
        else {
            //If unchecked, remove all nonmatching products of the currently checked boxes
            let found = filtered.filter(w => {return w.category.toLowerCase().indexOf(c.toLowerCase()) <= -1}) 
            filtered = found
        }
        //If everything is unchecked, all the products show
        if (document.querySelectorAll(".filters input:checked").length < 1) {filtered = []; ap(products) 
            return}
        //Update all products that match the selections
        ap(filtered)
    })
})
//Get all sorting options and loop through them
let sort = _s(".sort select")
sort.addEventListener("input", e=> {
    //On change, get the current sort value
    let sortValue = sort.options[sort.selectedIndex];
    console.log(sortValue)
    let sortWord = sortValue.dataset.sort 
    let sorted = []
    //If high to low, sort from high to low.
    if(sortWord == "hightolow") sorted = products.sort((a, b) => parseFloat(b.price) - parseFloat(a.price))
    //Else sort from low to high
    else sorted = products.sort((a, b) => parseFloat(a.price) - parseFloat(b.price))
    //Update the matching products in the DOM
    ap(sorted)
})