const track = document.querySelector('.scroll-track');
const images = document.querySelectorAll('.scroll-track img');
const leftArrow = document.querySelector('.scroll-arrow.left');
const rightArrow = document.querySelector('.scroll-arrow.right');
const dotsContainer = document.querySelector('.scroll-dots');

let index = 0;
const total = images.length;

// create dots
for(let i=0;i<total;i++){
    const dot = document.createElement('span');
    if(i===0) dot.classList.add('active');
    dotsContainer.appendChild(dot);
}
const dots = dotsContainer.querySelectorAll('span');

// update carousel
function updateCarousel(){
    const width = images[0].offsetWidth + 10; // include margin
    track.style.transform = `translateX(${-index * width}px)`;
    dots.forEach(dot => dot.classList.remove('active'));
    dots[index].classList.add('active');
}

// arrow clicks
leftArrow.addEventListener('click', () => {
    index = index > 0 ? index-1 : total-1;
    updateCarousel();
    resetAutoScroll();
});
rightArrow.addEventListener('click', () => {
    index = index < total-1 ? index+1 : 0;
    updateCarousel();
    resetAutoScroll();
});

// dot clicks
dotsContainer.addEventListener('click', e=>{
    if(e.target.tagName==='SPAN'){
        index = [...dotsContainer.children].indexOf(e.target);
        updateCarousel();
        resetAutoScroll();
    }
});

// auto-scroll
let autoScroll = setInterval(()=>{
    index = index < total-1 ? index+1 : 0;
    updateCarousel();
}, 3000);

// reset auto-scroll
function resetAutoScroll(){
    clearInterval(autoScroll);
    autoScroll = setInterval(()=>{
        index = index < total-1 ? index+1 : 0;
        updateCarousel();
    }, 3000);
}

// init
updateCarousel();
