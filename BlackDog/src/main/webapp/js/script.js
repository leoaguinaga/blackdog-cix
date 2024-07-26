const header = document.querySelector('header');

/*para cambiar el fondo del header */
window.addEventListener('scroll', () =>{
    header.classList.toggle('sticky', this.window.scrollY >80);
});