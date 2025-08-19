// Array of hero images (local or web URLs)
const heroImages = [
    'image/B.jpg',
    'image/A.jpg',
    'image/C.jpg',
    'image/D.jpg'
];

// Get the hero section
const heroSection = document.querySelector('.hero');

// Current image index
let currentIndex = 0;

// Function to change hero image
function changeHeroImage() {
    currentIndex = (currentIndex + 1) % heroImages.length;
    heroSection.style.backgroundImage = `url('${heroImages[currentIndex]}')`;
}

// Initial image load
heroSection.style.backgroundImage = `url('${heroImages[currentIndex]}')`;

// Change image every 5 seconds
setInterval(changeHeroImage, 5000);
