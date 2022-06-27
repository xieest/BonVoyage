# <p align="center">Bon Voyage</p>
# <p align="center"><img width="500" img height="300" src="https://images.unsplash.com/photo-1549937917-03ccda498729?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=464&q=80" alt="Bon Voyage"></p>

## Author:
- [Ester Xie](https://www.github.com/xieest)


## Overview:
Bon Voyage was created to showcase different locations around the world. I was inspired by travel blogs, and I wanted to combine my passion for reading and writing with a website that I could also browse flights with. In addition to being able to book flights, I also wanted users the option to shop for all of their travel essentials as well. Think of Bon Voyage as a one stop shop for travelers, whether they're looking to find their next location to visit, or to gain insight on places to visit! Traveling can be a huge stress reliever, and with covid hopefully winding down in the upcoming months/years, I hope that more people will be able to use my website to 
decide on their next travel destination. 

## Technical Specifications:
1. Database: MariaDB, HeidiSQL
2. Persistence Layer: Spring Data JPA
3. Business Layer: Java, Spring Boot, Spring Security
4. Presentation Layer: HTML, CSS, JS, Bootstrap, Thymeleaf

## User Stories:
- As a user, I'd like to be able to create an account.
- As a user, I'd like to be able to look at flights.
- As a user, I'd like to be able to book flights.
- As a user, I'd like to be able to add items to my cart.
- As a user, I'd like to be able to checkout my shopping cart.
- As a user, I'd like to access information about places to visit.

## Technical Challenges:
1) Stylizing the website how I wanted it to look. I wanted a clean, modern design, and it was tough to get everything to stylize correctly with CSS and HTML. I 
used Bootstrap on the flights page to populate a table, and it completely overrided my css and html. I had to reformat my stylizing so that the pages were more 
consistent, and doing that was tedious since it was process of elimination on what worked.
2) Bcrypt. Having to learn more about security was something that was definitely very interesting to me. However, since we only learned Spring Security in the 
class, I had to self-learn how to incorporate bcrypt with spring.
3) Web API. I wanted to use a free online web api to pull in flight data, so that it was accurate instead of creating mock flight data. As it turns out, most travel 
apis are monetized, and therefore, I wasn't able to find one with prices since I wasn't a travel partner. I managed to find Aviation Stack, which allowed me to pull 
100 free flight requests daily. As you can imagine, that's not a ton of data, so I had to choose 100 flights setting off for the next day. 

## Future Plans:
- Allow users to book hotels and cars as well. Bundle them up for more deals!
- Have prices next to flights to allow users to book flights. 
- Create a forum where travelers can review travel destinations, restaurants, and activities.
- Allow users to create a custom profile, so that they're able to favorite destinations.
- Suggest locations based on a survey that a user can fill out, and/or locations they've favorited.
